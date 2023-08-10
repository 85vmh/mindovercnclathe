package usecase

import codegen.Point
import com.mindovercnc.data.linuxcnc.CncCommandRepository
import com.mindovercnc.data.linuxcnc.CncStatusRepository
import com.mindovercnc.data.linuxcnc.HalRepository
import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.dispatchers.createScope
import com.mindovercnc.linuxcnc.gcode.IniFileRepository
import com.mindovercnc.linuxcnc.settings.SettingsRepository
import com.mindovercnc.model.UiMessage
import com.mindovercnc.repository.MessagesRepository
import extensions.stripZeros
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import linuxcnc.getDisplayablePosition
import ro.dragossusi.proto.linuxcnc.status.TaskMode
import usecase.model.AngleFinderState
import kotlin.math.abs

class AngleFinderUseCase(
    ioDispatcher: IoDispatcher,
    private val statusRepository: CncStatusRepository,
    private val commandRepository: CncCommandRepository,
    private val messagesRepository: MessagesRepository,
    private val halRepository: HalRepository,
    private val settingsRepository: SettingsRepository,
    private val iniFileRepository: IniFileRepository
) {

    private val scope = ioDispatcher.createScope()

    /**
     * Tre sa aleg pe care axa vreau sa traversez In functie de asta setez un starting point pe X sau
     * pe Z, dezactivez handwheel-ul celalalt Ma deplasez pe axa selectata un amount. Zic ca am
     * terminat si cate ture a facut ceasul. O sa fac un travel pe axa doar intre punctul initial si
     * punctul final, cu spindle oprit Travelul de verificare se face cu joystick la un feed
     * prestabilit, o sa actioneze joystickul in functie de axa selectata La final de cursa, intreb
     * daca ceasul arata 0 si daca nu, intreb care e diferenta. Recalculez si ii zic sa miste iar de
     * joytstick ca sa retraverseze
     */
    enum class TraverseAxis {
        X,
        Z
    }

    private val traverseFeedInUnitsPerMinute = 50
    private lateinit var startPoint: Point
    private lateinit var endPoint: Point
    private lateinit var axisToTraverse: TraverseAxis
    private var measuredDistance: Double? = null

    init {
        halRepository
            .getJoystickStatus()
            .filter {
                this::startPoint.isInitialized &&
                        this::endPoint.isInitialized &&
                        this::axisToTraverse.isInitialized
            }
            .onEach { joystickStatus ->
                println("---Joystick: $joystickStatus")
                //                when (joystickStatus.position) {
                //                    JoystickStatus.Position.ZMinus ->
                // handleJoystick(ManualTurningHelper.Axis.Z, ManualTurningHelper.Direction.Negative)
                //                    JoystickStatus.Position.ZPlus ->
                // handleJoystick(ManualTurningHelper.Axis.Z, ManualTurningHelper.Direction.Positive)
                //                    JoystickStatus.Position.XMinus ->
                // handleJoystick(ManualTurningHelper.Axis.X, ManualTurningHelper.Direction.Negative)
                //                    JoystickStatus.Position.XPlus ->
                // handleJoystick(ManualTurningHelper.Axis.X, ManualTurningHelper.Direction.Positive)
                //                    JoystickStatus.Position.Neutral -> handleBackToNeutral()
                //                }
            }
            .launchIn(scope)
    }

    fun angleFinderState(): AngleFinderState {
        return AngleFinderState(true, false, 45.0)
    }

    fun startProcedure(axisToTraverse: TraverseAxis) {
        // inhibit spindle start
        // inhibit the other jog wheel
        // inhibit joystick usage for now
        // store the starting point of the axis to be traversed.

        this.axisToTraverse = axisToTraverse
        scope.launch { startPoint = getCurrentPoint() }
    }

    fun setMeasuredDistance(value: Double) {
        this.measuredDistance = abs(value)
        scope.launch {
            val currentPoint = getCurrentPoint()
            endPoint =
                when (axisToTraverse) {
                    TraverseAxis.X -> {
                        // val sign = if (startPoint.x > currentPoint.x) 1 else -1
                        Point(x = currentPoint.x, z = currentPoint.z + value)
                    }

                    TraverseAxis.Z -> {
                        // val sign = if (startPoint.z > currentPoint.z) 1 else -1
                        Point(x = currentPoint.x + value, z = currentPoint.z)
                    }
                }
            println("----StartPoint: ${printMdiCommand(startPoint)}")
            println("----EndPoint: ${printMdiCommand(endPoint)}")
        }
        // display end point & calculated angle
    }

    private fun printMdiCommand(point: Point): String {
        return "G0 X${point.x.stripZeros()} Z${point.z.stripZeros()}"
    }

    //    private fun handleJoystick(axis: ManualTurningHelper.Axis, direction:
    // ManualTurningHelper.Direction) {
    //        val positivePoint: Point
    //        val negativePoint: Point
    //
    //        when (axisToTraverse) {
    //            TraverseAxis.X -> {
    //                positivePoint = if (startPoint.x > endPoint.x) startPoint else endPoint
    //                negativePoint = if (startPoint.x < endPoint.x) startPoint else endPoint
    //
    //                when (axis) {
    //                    ManualTurningHelper.Axis.Z ->
    // messagesRepository.pushMessage(UiMessage.JoystickDisabledOnZAxis)
    //                    ManualTurningHelper.Axis.X -> {
    //                        when (direction) {
    //                            ManualTurningHelper.Direction.Positive -> feedToPoint(positivePoint)
    //                            ManualTurningHelper.Direction.Negative -> feedToPoint(negativePoint)
    //                        }
    //                    }
    //                }
    //            }
    //            TraverseAxis.Z -> {
    //                positivePoint = if (startPoint.z > endPoint.z) startPoint else endPoint
    //                negativePoint = if (startPoint.z < endPoint.z) startPoint else endPoint
    //
    //                when (axis) {
    //                    ManualTurningHelper.Axis.X ->
    // messagesRepository.pushMessage(UiMessage.JoystickDisabledOnXAxis)
    //                    ManualTurningHelper.Axis.Z -> {
    //                        when (direction) {
    //                            ManualTurningHelper.Direction.Positive -> feedToPoint(positivePoint)
    //                            ManualTurningHelper.Direction.Negative -> feedToPoint(negativePoint)
    //                        }
    //                    }
    //                }
    //            }
    //        }
    //    }

    private fun feedToPoint(destinationPoint: Point) {
        executeMdi(
            "G94 G1 X${destinationPoint.x.stripZeros()} Z${destinationPoint.z.stripZeros()} F$traverseFeedInUnitsPerMinute"
        )
    }

    private fun executeMdi(command: String) {
        commandRepository.setTaskMode(TaskMode.TaskModeMDI)
        println("---Execute MDI: $command")
        commandRepository.executeMdiCommand(command)
        commandRepository.setTaskMode(TaskMode.TaskModeManual)
    }

    private fun handleBackToNeutral() {
        messagesRepository.popMessage(UiMessage.JoystickDisabledOnXAxis)
        messagesRepository.popMessage(UiMessage.JoystickDisabledOnZAxis)
        commandRepository.taskAbort()
        commandRepository.setTaskMode(TaskMode.TaskModeManual)
    }

    fun endProcedure() {
        // de-inhibit spindle start
        // de-inhibit the other jogwheel
        // reactivate normal joystick usage
    }

    private suspend fun getCurrentPoint() =
        statusRepository
            .cncStatusFlow
            .map { it.getDisplayablePosition() }
            .map { Point(it.x * 2, it.z) } // *2 due to diameter mode
            .first()
}
