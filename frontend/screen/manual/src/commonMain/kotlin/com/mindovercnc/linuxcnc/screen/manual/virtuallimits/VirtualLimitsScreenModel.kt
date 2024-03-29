package com.mindovercnc.linuxcnc.screen.manual.virtuallimits

import com.arkivanov.decompose.ComponentContext
import com.mindovercnc.linuxcnc.domain.PositionUseCase
import com.mindovercnc.linuxcnc.domain.VirtualLimitsUseCase
import com.mindovercnc.linuxcnc.domain.model.VirtualLimits
import com.mindovercnc.linuxcnc.screen.BaseScreenModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.instance

class VirtualLimitsScreenModel(
    di: DI,
    componentContext: ComponentContext,
) :
    BaseScreenModel<VirtualLimitsState>(VirtualLimitsState(), componentContext),
    VirtualLimitsComponent {

    private val positionUseCase: PositionUseCase by di.instance()
    private val virtualLimitsUseCase: VirtualLimitsUseCase by di.instance()

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

    override fun teachInXMinus() {
        coroutineScope.launch { setXMinus(positionUseCase.getCurrentPoint().x.toDouble()) }
    }

    override fun setXMinus(value: Double) {
        mutableState.update { it.copy(xMinus = value) }
    }

    override fun teachInXPlus() {
        coroutineScope.launch { setXPlus(positionUseCase.getCurrentPoint().x.toDouble()) }
    }

    override fun setXPlus(value: Double) {
        mutableState.update { it.copy(xPlus = value) }
    }

    override fun teachInZMinus() {
        coroutineScope.launch { setZMinus(positionUseCase.getCurrentPoint().y.toDouble()) }
    }

    override fun setZMinus(value: Double) {
        mutableState.update { it.copy(zMinus = value) }
    }

    override fun teachInZPlus() {
        coroutineScope.launch {
            val zLimit =
                when (mutableState.value.zPlusIsToolRelated) {
                    true -> positionUseCase.getCurrentPoint().y
                    false -> positionUseCase.getZMachinePosition()
                }
            setZPlus(zLimit.toDouble())
        }
    }

    override fun setZPlus(value: Double) {
        mutableState.update { it.copy(zPlus = value) }
    }

    override fun setXMinusActive(value: Boolean) {
        mutableState.update { it.copy(xMinusActive = value) }
    }

    override fun setXPlusActive(value: Boolean) {
        mutableState.update { it.copy(xPlusActive = value) }
    }

    override fun setZMinusActive(value: Boolean) {
        mutableState.update { it.copy(zMinusActive = value) }
    }

    override fun setZPlusActive(value: Boolean) {
        mutableState.update { it.copy(zPlusActive = value) }
    }

    override fun setZPlusToolRelated(value: Boolean) {
        mutableState.update { it.copy(zPlusIsToolRelated = value) }
    }

    override fun applyChanges() {
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
