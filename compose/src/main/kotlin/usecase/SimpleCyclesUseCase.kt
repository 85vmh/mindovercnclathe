package usecase

import com.mindovercnc.linuxcnc.model.TaskMode
import com.mindovercnc.repository.CncCommandRepository
import com.mindovercnc.repository.CncStatusRepository
import com.mindovercnc.repository.HalRepository
import com.mindovercnc.repository.SettingsRepository
import extensions.stripZeros
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import screen.uimodel.SimpleCycle
import usecase.model.SimpleCycleParameters

class SimpleCyclesUseCase(
    scope: CoroutineScope,
    private val statusRepository: CncStatusRepository,
    private val commandRepository: CncCommandRepository,
    halRepository: HalRepository,
    private val settingsRepository: SettingsRepository,
) {

    var isInEditMode = false
    private val _subroutineToCall = MutableStateFlow("")
    private val _simpleCycleParameters = MutableStateFlow<SimpleCycleParameters?>(null)

    private val subroutineToCall = _subroutineToCall.asStateFlow()
    val simpleCycleParameters = _simpleCycleParameters.asSharedFlow()

    init {
        halRepository.getCycleStopStatus()
            .filter { it }
            .filter { _simpleCycleParameters.value != null }
            .onEach {
                if (isCycleRunning()) {
                    commandRepository.taskAbort()
                    commandRepository.setTaskMode(TaskMode.TaskModeManual)
                } else {
                    _simpleCycleParameters.value = null
                }
            }.launchIn(scope)

        halRepository.getCycleStartStatus()
            .filter { it }
            .filter { _simpleCycleParameters.value != null && subroutineToCall.value.isNotEmpty() }
            .onEach {
                commandRepository.setTaskMode(TaskMode.TaskModeMDI)
                commandRepository.executeMdiCommand(subroutineToCall.value)
            }.launchIn(scope)
    }

    fun getCycleParameters(simpleCycle: SimpleCycle): SimpleCycleParameters {
        //if we have parameters for this cycle, return them
        _simpleCycleParameters.value?.let {
            if (it.simpleCycle == simpleCycle) {
                return it
            }
        }

        return when (simpleCycle) {
            SimpleCycle.Turning -> SimpleCycleParameters.TurningParameters(
                xEnd = 0.0,
                zEnd = 0.0,
                doc = 1.0
            )
            SimpleCycle.Boring -> SimpleCycleParameters.BoringParameters(
                xEnd = 0.0,
                zEnd = 0.0,
                doc = 1.0
            )
            SimpleCycle.Facing -> SimpleCycleParameters.FacingParameters(
                xEnd = 0.0,
                zEnd = 0.0,
                doc = 1.0
            )
            SimpleCycle.Threading -> SimpleCycleParameters.ThreadingParameters(
                pitch = 1.0,
                zEnd = 0.0,
                majorDiameter = 0.0,
                minorDiameter = 0.0,
                firstPassDepth = 0.2,
                finalDepth = 0.0
            )
            SimpleCycle.Drilling -> SimpleCycleParameters.DrillingParameters(
                zEnd = 0.0,
                retract = 0.0,
                increment = 0.0,
                rpm = 0.0,
                feed = 0.0
            )
            SimpleCycle.KeySlot -> SimpleCycleParameters.KeySlotParameters(
                zEnd = 0.0,
                xEnd = 0.0,
                doc = 0.1,
                feedPerMinute = 50.0
            )
            else -> SimpleCycleParameters.DummyParameters
        }
    }

    fun applyParameters(cycleParameters: SimpleCycleParameters) {
        val mdiCommand = when (cycleParameters) {
            is SimpleCycleParameters.TurningParameters -> getTurningCommand(cycleParameters)
            is SimpleCycleParameters.BoringParameters -> getBoringCommand(cycleParameters)
            is SimpleCycleParameters.FacingParameters -> getFacingCommand(cycleParameters)
            is SimpleCycleParameters.ThreadingParameters -> getThreadingCommand(cycleParameters)
            is SimpleCycleParameters.DrillingParameters -> getDrillingCommand(cycleParameters)
            is SimpleCycleParameters.KeySlotParameters -> getKeySlotCommand(cycleParameters)
            else -> "Not implemented yet"
        }
        println("---SimpleCycle MDI command: $mdiCommand")
        _subroutineToCall.value = mdiCommand
        _simpleCycleParameters.value = cycleParameters
    }

    private fun getTurningCommand(parameters: SimpleCycleParameters.TurningParameters): String {
        val xEnd = parameters.xEnd.stripZeros()
        val zEnd = parameters.zEnd.stripZeros()
        val doc = parameters.doc.stripZeros()
        val taperAngle = parameters.taperAngle.stripZeros()
        val filletRadius = parameters.filletRadius.stripZeros()
        return "o<turning> call [$xEnd] [$zEnd] [$doc] [$taperAngle] [$filletRadius]"
    }

    private fun getBoringCommand(parameters: SimpleCycleParameters.BoringParameters): String {
        val xEnd = parameters.xEnd.stripZeros()
        val zEnd = parameters.zEnd.stripZeros()
        val doc = parameters.doc.stripZeros()
        val turnAngle = parameters.taperAngle.stripZeros()
        val filletRadius = parameters.filletRadius.stripZeros()
        return "o<boring> call [$xEnd] [$zEnd] [$doc] [$turnAngle] [$filletRadius]"
    }

    private fun getFacingCommand(parameters: SimpleCycleParameters.FacingParameters): String {
        val xEnd = parameters.xEnd.stripZeros()
        val zEnd = parameters.zEnd.stripZeros()
        val doc = parameters.doc.stripZeros()
        return "o<facing> call [$xEnd] [$zEnd] [$doc]"
    }

    private fun getThreadingCommand(parameters: SimpleCycleParameters.ThreadingParameters): String {
        val pitch = parameters.pitch.stripZeros()
        val zEnd = parameters.zEnd.stripZeros()
        val startDiameter = when {
            parameters.isExternal -> parameters.majorDiameter.stripZeros()
            else -> parameters.minorDiameter.stripZeros()
        }
        val firstPassDoc = parameters.firstPassDepth.stripZeros()
        val finalDepth = parameters.finalDepth.stripZeros()
        val depthDegression = 1
        val infeedAngle = 30
        val taper = 0
        val springPasses = 0
        return "o<threading> call [$pitch] [$zEnd] [$startDiameter] [$firstPassDoc] [$finalDepth] [$depthDegression] [$infeedAngle] [2] [45] [$springPasses]"
    }

    private fun getDrillingCommand(parameters: SimpleCycleParameters.DrillingParameters): String {
        val zEnd = parameters.zEnd.stripZeros()
        val retract = parameters.retract.stripZeros()
        val increment = parameters.increment.stripZeros()
        val rpm = parameters.rpm.stripZeros()
        val feed = parameters.feed.stripZeros()
        return "o<drilling> call [$zEnd] [$retract] [$increment] [$rpm] [$feed]"
    }

    private fun getKeySlotCommand(parameters: SimpleCycleParameters.KeySlotParameters): String {
        val xEnd = parameters.xEnd.stripZeros()
        val zEnd = parameters.zEnd.stripZeros()
        val doc = parameters.doc.stripZeros()
        val feed = parameters.feedPerMinute.stripZeros()
        return "o<keyslot> call [$xEnd] [$zEnd] [$doc] [$feed]"
    }

    private suspend fun isCycleRunning() = statusRepository.cncStatusFlow()
        .map { it.isInMdiMode } //TODO: check also the interpreter state
        .first()
}