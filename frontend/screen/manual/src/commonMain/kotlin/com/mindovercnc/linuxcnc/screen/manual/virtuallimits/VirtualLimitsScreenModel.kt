package com.mindovercnc.linuxcnc.screen.manual.virtuallimits

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.mindovercnc.linuxcnc.domain.PositionUseCase
import com.mindovercnc.linuxcnc.domain.VirtualLimitsUseCase
import com.mindovercnc.linuxcnc.domain.model.VirtualLimits
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VirtualLimitsScreenModel(
    private val positionUseCase: PositionUseCase,
    private val virtualLimitsUseCase: VirtualLimitsUseCase,
) : StateScreenModel<VirtualLimitsState>(VirtualLimitsState()) {

    init {
        virtualLimitsUseCase
            .getSavedVirtualLimits()
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
            }
            .launchIn(coroutineScope)
    }

    fun teachInXMinus() {
        coroutineScope.launch { setXMinus(positionUseCase.getCurrentPoint().x.toDouble()) }
    }

    fun setXMinus(value: Double) {
        mutableState.update { it.copy(xMinus = value) }
    }

    fun teachInXPlus() {
        coroutineScope.launch { setXPlus(positionUseCase.getCurrentPoint().x.toDouble()) }
    }

    fun setXPlus(value: Double) {
        mutableState.update { it.copy(xPlus = value) }
    }

    fun teachInZMinus() {
        coroutineScope.launch { setZMinus(positionUseCase.getCurrentPoint().y.toDouble()) }
    }

    fun setZMinus(value: Double) {
        mutableState.update { it.copy(zMinus = value) }
    }

    fun teachInZPlus() {
        coroutineScope.launch {
            val zLimit =
                when (mutableState.value.zPlusIsToolRelated) {
                    true -> positionUseCase.getCurrentPoint().y
                    false -> positionUseCase.getZMachinePosition()
                }
            setZPlus(zLimit.toDouble())
        }
    }

    fun setZPlus(value: Double) {
        mutableState.update { it.copy(zPlus = value) }
    }

    fun setXMinusActive(value: Boolean) {
        mutableState.update { it.copy(xMinusActive = value) }
    }

    fun setXPlusActive(value: Boolean) {
        mutableState.update { it.copy(xPlusActive = value) }
    }

    fun setZMinusActive(value: Boolean) {
        mutableState.update { it.copy(zMinusActive = value) }
    }

    fun setZPlusActive(value: Boolean) {
        mutableState.update { it.copy(zPlusActive = value) }
    }

    fun setZPlusToolRelated(value: Boolean) {
        mutableState.update { it.copy(zPlusIsToolRelated = value) }
    }

    fun applyChanges() {
        virtualLimitsUseCase.saveVirtualLimits(mutableState.value.toVirtualLimits())
    }

    private fun VirtualLimitsState.toVirtualLimits() =
        VirtualLimits(
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
