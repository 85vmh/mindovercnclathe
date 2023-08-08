package ui.screen.manual.virtuallimits

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecase.PositionUseCase
import usecase.VirtualLimitsUseCase
import usecase.model.VirtualLimits

class VirtualLimitsScreenModel(
    private val positionUseCase: PositionUseCase,
    private val virtualLimitsUseCase: VirtualLimitsUseCase,
) : StateScreenModel<VirtualLimitsScreenModel.State>(State()) {

    data class State(
        val xMinus: Double = 0.0,
        val xPlus: Double = 0.0,
        val zMinus: Double = 0.0,
        val zPlus: Double = 0.0,
        val xMinusActive: Boolean = false,
        val xPlusActive: Boolean = false,
        val zMinusActive: Boolean = false,
        val zPlusActive: Boolean = false,
        val zPlusIsToolRelated: Boolean = false
    )

    init {
        virtualLimitsUseCase.getSavedVirtualLimits()
            .onEach { newLimits ->
                with(newLimits) {
                    mutableState.update {
                        it.copy(
                            xMinus = xMinus,
                            xPlus = xPlus,
                            zMinus = zMinus,
                            zPlus = zPlus,
                            xMinusActive = xMinusActive,
                            xPlusActive = xPlusActive,
                            zMinusActive = zMinusActive,
                            zPlusActive = zPlusActive,
                            zPlusIsToolRelated = zPlusIsToolRelated
                        )
                    }
                }
            }.launchIn(coroutineScope)
    }

    fun teachInXMinus() {
        coroutineScope.launch {
            setXMinus(positionUseCase.getCurrentPoint().x.toDouble())
        }
    }

    fun setXMinus(value: Double) {
        mutableState.update {
            it.copy(xMinus = value)
        }
    }

    fun teachInXPlus() {
        coroutineScope.launch {
            setXPlus(positionUseCase.getCurrentPoint().x.toDouble())
        }
    }

    fun setXPlus(value: Double) {
        mutableState.update {
            it.copy(xPlus = value)
        }
    }

    fun teachInZMinus() {
        coroutineScope.launch {
            setZMinus(positionUseCase.getCurrentPoint().y.toDouble())
        }
    }

    fun setZMinus(value: Double) {
        mutableState.update {
            it.copy(zMinus = value)
        }
    }

    fun teachInZPlus() {
        coroutineScope.launch {
            val zLimit = when (mutableState.value.zPlusIsToolRelated) {
                true -> positionUseCase.getCurrentPoint().y
                false -> positionUseCase.getZMachinePosition()
            }
            setZPlus(zLimit.toDouble())
        }
    }

    fun setZPlus(value: Double) {
        mutableState.update {
            it.copy(zPlus = value)
        }
    }

    fun setXMinusActive(value: Boolean) {
        mutableState.update {
            it.copy(xMinusActive = value)
        }
    }

    fun setXPlusActive(value: Boolean) {
        mutableState.update {
            it.copy(xPlusActive = value)
        }
    }

    fun setZMinusActive(value: Boolean) {
        mutableState.update {
            it.copy(zMinusActive = value)
        }
    }

    fun setZPlusActive(value: Boolean) {
        mutableState.update {
            it.copy(zPlusActive = value)
        }
    }

    fun setZPlusToolRelated(value: Boolean) {
        mutableState.update {
            it.copy(zPlusIsToolRelated = value)
        }
    }

    fun applyChanges() {
        virtualLimitsUseCase.saveVirtualLimits(mutableState.value.toVirtualLimits())
    }

    private fun State.toVirtualLimits() = VirtualLimits(
        xMinus = xMinus,
        xPlus = xPlus,
        zMinus = zMinus,
        zPlus = zPlus,
        xMinusActive = xMinusActive,
        xPlusActive = xPlusActive,
        zMinusActive = zMinusActive,
        zPlusActive = zPlusActive,
        zPlusIsToolRelated = zPlusIsToolRelated
    )
}