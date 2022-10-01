package ui.screen.manual.simplecycles

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import screen.uimodel.SimpleCycle
import usecase.PositionUseCase
import usecase.SimpleCyclesUseCase
import usecase.model.SimpleCycleParameters
import kotlin.math.sqrt

class SimpleCyclesScreenModel(
    simpleCycle: SimpleCycle,
    private val positionUseCase: PositionUseCase,
    private val simpleCyclesUseCase: SimpleCyclesUseCase
) : StateScreenModel<SimpleCyclesScreenModel.State>(State()) {

    data class State(
        val simpleCycleParameters: SimpleCycleParameters? = null
    )

    init {
        val cycleParameters = simpleCyclesUseCase.getCycleParameters(simpleCycle)
        coroutineScope.launch {
            mutableState.update {
                it.copy(simpleCycleParameters = cycleParameters)
            }
        }
    }

    fun enterEditMode() {
        simpleCyclesUseCase.isInEditMode = true
    }

    fun exitEditMode() {
        simpleCyclesUseCase.isInEditMode = false
    }

    fun applyChanges() {
        mutableState.value.simpleCycleParameters?.let {
            simpleCyclesUseCase.applyParameters(it)
            simpleCyclesUseCase.isInEditMode = false
        }
    }

    fun teachInXEnd() {
        coroutineScope.launch {
            setXEnd(positionUseCase.getCurrentPoint().x)
        }
    }

    fun setXEnd(xEnd: Double) {
        mutableState.value.simpleCycleParameters?.let { actualParameters ->
            when (actualParameters) {
                is SimpleCycleParameters.TurningParameters -> actualParameters.copy(xEnd = xEnd)
                is SimpleCycleParameters.BoringParameters -> actualParameters.copy(xEnd = xEnd)
                is SimpleCycleParameters.FacingParameters -> actualParameters.copy(xEnd = xEnd)
                is SimpleCycleParameters.KeySlotParameters -> actualParameters.copy(xEnd = xEnd)
                else -> null
            }?.let { newParams ->
                mutableState.update {
                    it.copy(simpleCycleParameters = newParams)
                }
            }
        }
    }

    fun teachInMajorDiameter() {
        coroutineScope.launch {
            setMajorDiameter(positionUseCase.getCurrentPoint().x)
        }
    }

    fun setMajorDiameter(xCoordinate: Double) {
        mutableState.value.simpleCycleParameters?.let { actualParameters ->
            when (actualParameters) {
                is SimpleCycleParameters.ThreadingParameters -> actualParameters.copy(majorDiameter = xCoordinate)
                else -> null
            }?.let { newParams ->
                mutableState.update {
                    it.copy(simpleCycleParameters = newParams)
                }
            }
        }
    }

    fun teachInMinorDiameter() {
        coroutineScope.launch {
            setMinorDiameter(positionUseCase.getCurrentPoint().x)
        }
    }

    fun setMinorDiameter(xCoordinate: Double) {
        mutableState.value.simpleCycleParameters?.let { actualParameters ->
            when (actualParameters) {
                is SimpleCycleParameters.ThreadingParameters -> actualParameters.copy(minorDiameter = xCoordinate)
                else -> null
            }?.let { newParams ->
                mutableState.update {
                    it.copy(simpleCycleParameters = newParams)
                }
            }
        }
    }

    fun calculateFinalDepth() {
        coroutineScope.launch {
            mutableState.value.simpleCycleParameters?.let { actualParameters ->
                when (actualParameters) {
                    is SimpleCycleParameters.ThreadingParameters -> {
                        val finalDepth = calculateFinalDepth(actualParameters.pitch)
                        when {
                            actualParameters.isExternal -> actualParameters.copy(
                                finalDepth = finalDepth,
                                minorDiameter = actualParameters.majorDiameter - 2 * finalDepth
                            )
                            else -> actualParameters.copy(
                                finalDepth = finalDepth,
                                majorDiameter = actualParameters.minorDiameter + 2 * finalDepth
                            )
                        }
                    }
                    else -> null
                }?.let { newParams ->
                    mutableState.update {
                        it.copy(simpleCycleParameters = newParams)
                    }
                }
            }
        }
    }

    private fun calculateFinalDepth(threadPitch: Double): Double {
        val triangleHeight = sqrt(3.0) / 2 * threadPitch
        //return 5 / 8 * triangleHeight
        return triangleHeight
    }

    fun teachInZEnd() {
        coroutineScope.launch {
            setZEnd(positionUseCase.getCurrentPoint().z)
        }
    }

    fun setZEnd(zCoordinate: Double) {
        mutableState.value.simpleCycleParameters?.let { actualParameters ->
            when (actualParameters) {
                is SimpleCycleParameters.TurningParameters -> actualParameters.copy(zEnd = zCoordinate)
                is SimpleCycleParameters.BoringParameters -> actualParameters.copy(zEnd = zCoordinate)
                is SimpleCycleParameters.FacingParameters -> actualParameters.copy(zEnd = zCoordinate)
                is SimpleCycleParameters.ThreadingParameters -> actualParameters.copy(zEnd = zCoordinate)
                is SimpleCycleParameters.DrillingParameters -> actualParameters.copy(zEnd = zCoordinate)
                is SimpleCycleParameters.KeySlotParameters -> actualParameters.copy(zEnd = zCoordinate)
                else -> null
            }?.let { newParams ->
                mutableState.update {
                    it.copy(simpleCycleParameters = newParams)
                }
            }
        }
    }

    fun setDoc(doc: Double) {
        coroutineScope.launch {
            mutableState.value.simpleCycleParameters?.let { actualParameters ->
                when (actualParameters) {
                    is SimpleCycleParameters.TurningParameters -> actualParameters.copy(doc = doc)
                    is SimpleCycleParameters.BoringParameters -> actualParameters.copy(doc = doc)
                    is SimpleCycleParameters.FacingParameters -> actualParameters.copy(doc = doc)
                    is SimpleCycleParameters.KeySlotParameters -> actualParameters.copy(doc = doc)
                    else -> null
                }.let { newParams ->
                    mutableState.update {
                        it.copy(simpleCycleParameters = newParams)
                    }
                }
            }
        }
    }

    fun setThreadPitch(threadPitch: Double) {
        coroutineScope.launch {
            mutableState.value.simpleCycleParameters?.let { actualParameters ->
                when (actualParameters) {
                    is SimpleCycleParameters.ThreadingParameters -> actualParameters.copy(pitch = threadPitch)
                    else -> null
                }?.let { newParams ->
                    mutableState.update {
                        it.copy(simpleCycleParameters = newParams)
                    }
                }
            }
        }
    }

    fun setFirstPassDepth(depth: Double) {
        coroutineScope.launch {
            mutableState.value.simpleCycleParameters?.let { actualParameters ->
                when (actualParameters) {
                    is SimpleCycleParameters.ThreadingParameters -> actualParameters.copy(firstPassDepth = depth)
                    else -> null
                }?.let { newParams ->
                    mutableState.update {
                        it.copy(simpleCycleParameters = newParams)
                    }
                }
            }
        }
    }

    fun setFinalPassDepth(depth: Double) {
        coroutineScope.launch {
            mutableState.value.simpleCycleParameters?.let { actualParameters ->
                when (actualParameters) {
                    is SimpleCycleParameters.ThreadingParameters -> actualParameters.copy(finalDepth = depth)
                    else -> null
                }?.let { newParams ->
                    mutableState.update {
                        it.copy(simpleCycleParameters = newParams)
                    }
                }
            }
        }
    }

    fun setThreadSpringPasses(passes: Int) {
        coroutineScope.launch {
            mutableState.value.simpleCycleParameters?.let { actualParameters ->
                when (actualParameters) {
                    is SimpleCycleParameters.ThreadingParameters -> actualParameters.copy(springPasses = passes)
                    else -> null
                }?.let { newParams ->
                    mutableState.update {
                        it.copy(simpleCycleParameters = newParams)
                    }
                }
            }
        }
    }

    fun setTaperAngle(it: Double) {

    }

    fun setFilletRadius(it: Double) {

    }

    fun setThreadLocation(isExternal: Boolean) {
        coroutineScope.launch {
            mutableState.value.simpleCycleParameters?.let { actualParameters ->
                when (actualParameters) {
                    is SimpleCycleParameters.ThreadingParameters -> actualParameters.copy(isExternal = isExternal)
                    else -> null
                }.let { newParams ->
                    mutableState.update {
                        it.copy(simpleCycleParameters = newParams)
                    }
                }
            }
        }
    }

    fun setThreadType(it: SimpleCycleParameters.ThreadingParameters.ThreadType) {
        coroutineScope.launch {
            mutableState.value.simpleCycleParameters?.let { actualParameters ->
                when (actualParameters) {
                    is SimpleCycleParameters.ThreadingParameters -> actualParameters.copy(threadType = it)
                    else -> null
                }.let { newParams ->
                    mutableState.update {
                        it.copy(simpleCycleParameters = newParams)
                    }
                }
            }
        }
    }

    fun setKeySlotCuttingFeed(feed: Double) {
        coroutineScope.launch {
            mutableState.value.simpleCycleParameters?.let { actualParameters ->
                when (actualParameters) {
                    is SimpleCycleParameters.KeySlotParameters -> actualParameters.copy(feedPerMinute = feed)
                    else -> null
                }.let { newParams ->
                    mutableState.update {
                        it.copy(simpleCycleParameters = newParams)
                    }
                }
            }
        }
    }
}