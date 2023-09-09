package com.mindovercnc.linuxcnc.screen.manual.simplecycles

import com.arkivanov.decompose.ComponentContext
import com.mindovercnc.linuxcnc.domain.PositionUseCase
import com.mindovercnc.linuxcnc.domain.SimpleCyclesUseCase
import com.mindovercnc.linuxcnc.screen.BaseScreenModel
import com.mindovercnc.model.SimpleCycle
import com.mindovercnc.model.SimpleCycleParameters
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.instance
import kotlin.math.sqrt

class SimpleCyclesScreenModel(
    di: DI,
    componentContext: ComponentContext,
) :
    BaseScreenModel<SimpleCyclesState>(SimpleCyclesState(), componentContext),
    SimpleCyclesComponent {

    private val simpleCycle: SimpleCycle by di.instance()
    private val positionUseCase: PositionUseCase by di.instance()
    private val simpleCyclesUseCase: SimpleCyclesUseCase by di.instance()

    init {
        val cycleParameters = simpleCyclesUseCase.getCycleParameters(simpleCycle)
        coroutineScope.launch {
            mutableState.update { it.copy(simpleCycleParameters = cycleParameters) }
        }
    }

    override fun enterEditMode() {
        simpleCyclesUseCase.isInEditMode = true
    }

    override fun exitEditMode() {
        simpleCyclesUseCase.isInEditMode = false
    }

    override fun applyChanges() {
        mutableState.value.simpleCycleParameters?.let {
            simpleCyclesUseCase.applyParameters(it)
            simpleCyclesUseCase.isInEditMode = false
        }
    }

    override fun teachInXEnd() {
        coroutineScope.launch { setXEnd(positionUseCase.getCurrentPoint().x.toDouble()) }
    }

    override fun setXEnd(xEnd: Double) {
        mutableState.value.simpleCycleParameters?.let { actualParameters ->
            when (actualParameters) {
                is SimpleCycleParameters.TurningParameters -> actualParameters.copy(xEnd = xEnd)
                is SimpleCycleParameters.BoringParameters -> actualParameters.copy(xEnd = xEnd)
                is SimpleCycleParameters.FacingParameters -> actualParameters.copy(xEnd = xEnd)
                is SimpleCycleParameters.KeySlotParameters -> actualParameters.copy(xEnd = xEnd)
                else -> null
            }?.let { newParams ->
                mutableState.update { it.copy(simpleCycleParameters = newParams) }
            }
        }
    }

    override fun teachInMajorDiameter() {
        coroutineScope.launch { setMajorDiameter(positionUseCase.getCurrentPoint().x.toDouble()) }
    }

    override fun setMajorDiameter(xCoordinate: Double) {
        mutableState.value.simpleCycleParameters?.let { actualParameters ->
            when (actualParameters) {
                is SimpleCycleParameters.ThreadingParameters ->
                    actualParameters.copy(majorDiameter = xCoordinate)
                else -> null
            }?.let { newParams ->
                mutableState.update { it.copy(simpleCycleParameters = newParams) }
            }
        }
    }

    override fun teachInMinorDiameter() {
        coroutineScope.launch { setMinorDiameter(positionUseCase.getCurrentPoint().x.toDouble()) }
    }

    override fun setMinorDiameter(xCoordinate: Double) {
        mutableState.value.simpleCycleParameters?.let { actualParameters ->
            when (actualParameters) {
                is SimpleCycleParameters.ThreadingParameters ->
                    actualParameters.copy(minorDiameter = xCoordinate)
                else -> null
            }?.let { newParams ->
                mutableState.update { it.copy(simpleCycleParameters = newParams) }
            }
        }
    }

    override fun calculateFinalDepth() {
        coroutineScope.launch {
            mutableState.value.simpleCycleParameters?.let { actualParameters ->
                when (actualParameters) {
                    is SimpleCycleParameters.ThreadingParameters -> {
                        val finalDepth = calculateFinalDepth(actualParameters.pitch)
                        when {
                            actualParameters.isExternal -> {
                                actualParameters.copy(
                                    finalDepth = finalDepth,
                                    minorDiameter = actualParameters.majorDiameter - 2 * finalDepth
                                )
                            }
                            else -> {
                                actualParameters.copy(
                                    finalDepth = finalDepth,
                                    majorDiameter = actualParameters.minorDiameter + 2 * finalDepth
                                )
                            }
                        }
                    }
                    else -> null
                }?.let { newParams ->
                    mutableState.update { it.copy(simpleCycleParameters = newParams) }
                }
            }
        }
    }

    private fun calculateFinalDepth(threadPitch: Double): Double {
        val triangleHeight = sqrt(3.0) / 2 * threadPitch
        // return 5 / 8 * triangleHeight
        return triangleHeight
    }

    override fun teachInZEnd() {
        coroutineScope.launch { setZEnd(positionUseCase.getCurrentPoint().y.toDouble()) }
    }

    override fun setZEnd(zCoordinate: Double) {
        mutableState.value.simpleCycleParameters?.let { actualParameters ->
            when (actualParameters) {
                is SimpleCycleParameters.TurningParameters ->
                    actualParameters.copy(zEnd = zCoordinate)
                is SimpleCycleParameters.BoringParameters ->
                    actualParameters.copy(zEnd = zCoordinate)
                is SimpleCycleParameters.FacingParameters ->
                    actualParameters.copy(zEnd = zCoordinate)
                is SimpleCycleParameters.ThreadingParameters ->
                    actualParameters.copy(zEnd = zCoordinate)
                is SimpleCycleParameters.DrillingParameters ->
                    actualParameters.copy(zEnd = zCoordinate)
                is SimpleCycleParameters.KeySlotParameters ->
                    actualParameters.copy(zEnd = zCoordinate)
                else -> null
            }?.let { newParams ->
                mutableState.update { it.copy(simpleCycleParameters = newParams) }
            }
        }
    }

    override fun setDoc(doc: Double) {
        coroutineScope.launch {
            mutableState.value.simpleCycleParameters?.let { actualParameters ->
                when (actualParameters) {
                    is SimpleCycleParameters.TurningParameters -> actualParameters.copy(doc = doc)
                    is SimpleCycleParameters.BoringParameters -> actualParameters.copy(doc = doc)
                    is SimpleCycleParameters.FacingParameters -> actualParameters.copy(doc = doc)
                    is SimpleCycleParameters.KeySlotParameters -> actualParameters.copy(doc = doc)
                    else -> null
                }.let { newParams ->
                    mutableState.update { it.copy(simpleCycleParameters = newParams) }
                }
            }
        }
    }

    override fun setThreadPitch(threadPitch: Double) {
        coroutineScope.launch {
            mutableState.value.simpleCycleParameters?.let { actualParameters ->
                when (actualParameters) {
                    is SimpleCycleParameters.ThreadingParameters ->
                        actualParameters.copy(pitch = threadPitch)
                    else -> null
                }?.let { newParams ->
                    mutableState.update { it.copy(simpleCycleParameters = newParams) }
                }
            }
        }
    }

    override fun setFirstPassDepth(depth: Double) {
        coroutineScope.launch {
            mutableState.value.simpleCycleParameters?.let { actualParameters ->
                when (actualParameters) {
                    is SimpleCycleParameters.ThreadingParameters ->
                        actualParameters.copy(firstPassDepth = depth)
                    else -> null
                }?.let { newParams ->
                    mutableState.update { it.copy(simpleCycleParameters = newParams) }
                }
            }
        }
    }

    override fun setFinalPassDepth(depth: Double) {
        coroutineScope.launch {
            mutableState.value.simpleCycleParameters?.let { actualParameters ->
                when (actualParameters) {
                    is SimpleCycleParameters.ThreadingParameters ->
                        actualParameters.copy(finalDepth = depth)
                    else -> null
                }?.let { newParams ->
                    mutableState.update { it.copy(simpleCycleParameters = newParams) }
                }
            }
        }
    }

    override fun setThreadSpringPasses(passes: Int) {
        mutableState.update { state ->
            val newParams =
                state.simpleCycleParameters as? SimpleCycleParameters.ThreadingParameters?
            if (newParams != null) {
                state.copy(simpleCycleParameters = newParams.copy(springPasses = passes))
            } else {
                state
            }
        }
    }

    override fun setTaperAngle(angle: Double) {
        // TODO: implement
    }

    override fun setFilletRadius(radius: Double) {
        // TODO: implement
    }

    override fun setThreadLocation(isExternal: Boolean) {
        mutableState.update { state ->
            val newParams =
                state.simpleCycleParameters as? SimpleCycleParameters.ThreadingParameters?
            if (newParams != null) {
                state.copy(simpleCycleParameters = newParams.copy(isExternal = isExternal))
            } else {
                state
            }
        }
    }

    override fun setThreadType(threadType: SimpleCycleParameters.ThreadingParameters.ThreadType) {
        mutableState.update { state ->
            val newParams =
                state.simpleCycleParameters as? SimpleCycleParameters.ThreadingParameters?
            if (newParams != null) {
                state.copy(simpleCycleParameters = newParams.copy(threadType = threadType))
            } else {
                state
            }
        }
    }

    override fun setKeySlotCuttingFeed(feed: Double) {
        mutableState.update { state ->
            val newParams = state.simpleCycleParameters as? SimpleCycleParameters.KeySlotParameters?
            if (newParams != null) {
                state.copy(simpleCycleParameters = newParams.copy(feedPerMinute = feed))
            } else {
                state
            }
        }
    }
}
