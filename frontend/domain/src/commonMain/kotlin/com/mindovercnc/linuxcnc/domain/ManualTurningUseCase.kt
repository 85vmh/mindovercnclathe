package com.mindovercnc.linuxcnc.domain

import com.mindovercnc.data.linuxcnc.CncCommandRepository
import com.mindovercnc.data.linuxcnc.CncStatusRepository
import com.mindovercnc.data.linuxcnc.HalRepository
import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.dispatchers.createScope
import com.mindovercnc.linuxcnc.format.stripZeros
import com.mindovercnc.linuxcnc.gcode.IniFileRepository
import com.mindovercnc.linuxcnc.settings.BooleanKey
import com.mindovercnc.linuxcnc.settings.DoubleKey
import com.mindovercnc.linuxcnc.settings.IntegerKey
import com.mindovercnc.linuxcnc.settings.SettingsRepository
import com.mindovercnc.model.*
import com.mindovercnc.model.codegen.CodegenPoint
import com.mindovercnc.repository.MessagesRepository
import com.mindovercnc.repository.MotionStatusRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import linuxcnc.*
import ro.dragossusi.proto.linuxcnc.status.JogMode
import ro.dragossusi.proto.linuxcnc.status.TaskMode

class ManualTurningUseCase(
    ioDispatcher: IoDispatcher,
    private val cncStatusRepository: CncStatusRepository,
    private val motionStatusRepository: MotionStatusRepository,
    private val commandRepository: CncCommandRepository,
    private val messagesRepository: MessagesRepository,
    private val halRepository: HalRepository,
    private val settingsRepository: SettingsRepository,
    private val iniFileRepository: IniFileRepository
) {

    private val scope = ioDispatcher.createScope()

    private var feedJob: Job? = null
    private var joystickFunction = JoystickFunction.None
    private var joggedAxis: Axis? = null
    private var joystickResetRequired = false

    private val isTaperTurning = MutableStateFlow(false)
    private val taperAngle
        get() = 45.0

    enum class JoystickFunction {
        None,
        Feeding,
        Jogging
    }

    val taperTurningActive = isTaperTurning.asStateFlow()

    private val spindleOpAllowed =
        motionStatusRepository.motionStatusFlow.map { it.isHomed(2) }.distinctUntilChanged()

    init {
        val spindleIsOn =
            motionStatusRepository.motionStatusFlow
                .map { it.isSpindleOn } // do this based on tool direction
                .distinctUntilChanged()

        combine(halRepository.getJoystickStatus(), spindleIsOn) { joystickStatus, spindleOn ->
            println("---Spindle: $spindleOn, Joystick: $joystickStatus")
            handleJoystick(joystickStatus, spindleOn)
        }
            .launchIn(scope)

        combine(
            halRepository.getSpindleSwitchStatus().onEach { println("---Spindle switch is: $it") },
            spindleOpAllowed
        ) { switchStatus, spindleAllowed ->
            when {
                spindleAllowed -> sendSpindleCommand(switchStatus)
                spindleAllowed.not() -> {
                    when (switchStatus) {
                        SpindleSwitchStatus.NEUTRAL ->
                            messagesRepository.popMessage(UiMessage.SpindleOperationNotAllowed)

                        else -> messagesRepository.pushMessage(UiMessage.SpindleOperationNotAllowed)
                    }
                }
            }
        }
            .launchIn(scope)

        halRepository
            .getCycleStopStatus()
            .filter { it }
            .onEach {
                if (isTaperTurning.value) {
                    isTaperTurning.value = false
                    stopFeeding()
                }
            }
            .launchIn(scope)

        halRepository.getCycleStartStatus().filter { it }.onEach {}.launchIn(scope)
    }

    fun toggleTaperTurning() {
        isTaperTurning.value = isTaperTurning.value.not()
    }

    private suspend fun sendSpindleCommand(status: SpindleSwitchStatus) {
        when (status) {
            SpindleSwitchStatus.REV -> "M4"
            SpindleSwitchStatus.FWD -> "M3"
            SpindleSwitchStatus.NEUTRAL -> {
                halRepository.setSpindleStarted(false)
                isTaperTurning.value = false
                null // for now spindle stop is done in HAL and Classic Ladder
            }
        }?.let {
            val cmd = it + settingsRepository.getSpindleStartParameters()

            //            if (!statusRepository.cncStatusFlow.first().isInMdiMode) {
            //                commandRepository.setTaskMode(TaskMode.TaskModeMDI)
            //            }
            commandRepository.setTaskMode(TaskMode.TaskModeMDI)
            val result = commandRepository.executeMdiCommand(cmd)
            println("---MDI spindle command is: $result")
            halRepository.setSpindleStarted(true)
            delay(200L) // delay a bit switching to manual
            commandRepository.setTaskMode(TaskMode.TaskModeManual)
        }
    }

    private fun setHalSpindleStatus(status: SpindleSwitchStatus) {
        halRepository.setSpindleStarted(status != SpindleSwitchStatus.NEUTRAL)
    }

    private suspend fun handleJoystick(joystickStatus: JoystickStatus, isSpindleOn: Boolean) {
        if (joystickStatus.position == JoystickStatus.Position.Neutral) {
            handleJoystickNeutral()
            return
        }

        val axis = joystickStatus.position.axis!!
        val direction = joystickStatus.position.direction!!

        if (joystickStatus.isRapid) {
            startJogging(axis, direction)
            joystickResetRequired = true
        } else if (joystickFunction == JoystickFunction.Jogging) {
            stopJogging(axis)
            joystickResetRequired = true
        }

        if (isSpindleOn) {
            if (joystickResetRequired) {
                println("---Joystick is not in neutral state")
                messagesRepository.pushMessage(UiMessage.JoystickResetRequired)
            } else {
                delayedFeed(axis, direction)
            }
        } else {
            when (joystickFunction) {
                JoystickFunction.Feeding -> {
                    println("---Spindle was stopped while feeding, stopFeeding!")
                    stopFeeding()
                    joystickResetRequired = true
                }

                JoystickFunction.Jogging -> {
                    // nothing to do here
                }

                JoystickFunction.None -> {
                    println("---Feed attempted while spindle is off")
                    messagesRepository.pushMessage(UiMessage.JoystickCannotFeedWithSpindleOff)
                }
            }
        }
    }

    private suspend fun delayedFeed(axis: Axis, direction: Direction) {
        println("---Delayed feed")
        feedJob?.cancel()
        feedJob =
            scope.launch {
                delay(200L)
                println("---Delay passed, start feeding")
                startFeeding(axis, direction)
            }
    }

    private suspend fun startFeeding(axis: Axis, direction: Direction) {
        val command =
            when {
                isTaperTurning.value -> {
                    val startPoint =
                        cncStatusRepository.cncStatusFlow
                            .map { it.g53Position!! }
                            .map { CodegenPoint(it.x * 2, it.z) } // *2 due to diameter mode
                            .first()
                    ManualTurningHelper.getTaperTurningCommand(
                        axis,
                        direction,
                        iniFileRepository.getActiveLimits(),
                        startPoint,
                        taperAngle
                    )
                }

                else ->
                    ManualTurningHelper.getStraightTurningCommand(
                        axis,
                        direction,
                        iniFileRepository.getActiveLimits().inSafeRange()
                    )
            }
        val feedRate = setFeedRate.firstOrNull() ?: 0.0
        joystickFunction = JoystickFunction.Feeding
        executeMdi(command.plus(" F${feedRate.stripZeros()}"))
        halRepository.setPowerFeedingStatus(true)
    }

    private fun handleJoystickNeutral() {
        println("---handleJoystickNeutral()")
        feedJob?.cancel()
        when (joystickFunction) {
            JoystickFunction.Feeding -> stopFeeding()
            JoystickFunction.Jogging -> joggedAxis?.let { stopJogging(it) }
            JoystickFunction.None -> {
                joggedAxis = null
                messagesRepository.popMessage(UiMessage.JoystickCannotFeedWithSpindleOff)
            }
        }
        if (joystickResetRequired) {
            joystickResetRequired = false
            messagesRepository.popMessage(UiMessage.JoystickResetRequired)
        }
    }

    private fun stopFeeding(): Boolean {
        feedJob?.cancel()
        if (joystickFunction == JoystickFunction.Feeding) {
            halRepository.setPowerFeedingStatus(false)
            joystickFunction = JoystickFunction.None
            println("---Stop feeding")
            return true
        }
        return false
    }

    private suspend fun startJogging(axis: Axis, feedDirection: Direction) {
        println("---Start jogging")
        if (stopFeeding()) {
            delay(100L)
        }
        commandRepository.setTaskMode(TaskMode.TaskModeManual)
        val jogVelocity = motionStatusRepository.motionStatusFlow.map { it.jogVelocity!! }.first()
        val jogDirection =
            when (feedDirection) {
                Direction.Positive -> jogVelocity
                Direction.Negative -> jogVelocity * -1
            }
        println("---Jog $axis with velocity: $jogDirection")
        commandRepository.jogContinuous(JogMode.JOG_AXIS, axis.index, jogDirection)
        joggedAxis = axis
        joystickFunction = JoystickFunction.Jogging
    }

    private fun stopJogging(axis: Axis) {
        if (joystickFunction == JoystickFunction.Jogging) {
            commandRepository.setTaskMode(TaskMode.TaskModeManual)
            commandRepository.jogStop(JogMode.JOG_AXIS, axis.index)
            joystickFunction = JoystickFunction.None
            println("---Stop jogging")
        }
    }

    private fun executeMdi(command: String) {
        commandRepository.setTaskMode(TaskMode.TaskModeMDI)
        commandRepository.executeMdiCommand(command)
    }

    private val setFeedRate =
        cncStatusRepository.cncStatusFlow.map { it.task_status!!.setFeedRate }.distinctUntilChanged()

    private fun SettingsRepository.getSpindleStartParameters(): String {
        val parameters = StringBuilder()
        when (settingsRepository.get(BooleanKey.IsRpmMode)) {
            true -> {
                val rpmSpeed = get(IntegerKey.RpmValue, 300)
                parameters.append(" G97 S$rpmSpeed")
            }

            else -> {
                val cssValue = get(IntegerKey.CssValue, 230)
                val cssMaxRpm = get(IntegerKey.MaxCssRpm, 1500)
                parameters.append(" G96 D$cssMaxRpm S$cssValue")
            }
        }
        when (settingsRepository.get(BooleanKey.IsUnitsPerRevMode)) {
            true -> {
                val feedPerRev = settingsRepository.get(DoubleKey.FeedPerRev, 0.1)
                parameters.append(" G95 F$feedPerRev")
            }

            else -> {
                val feedPerMin = settingsRepository.get(DoubleKey.FeedPerMin, 50.0)
                parameters.append(" G94 F$feedPerMin")
            }
        }

        return parameters.toString()
    }
}
