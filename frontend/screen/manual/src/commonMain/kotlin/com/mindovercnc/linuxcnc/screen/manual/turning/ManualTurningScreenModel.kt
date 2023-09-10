package com.mindovercnc.linuxcnc.screen.manual.turning

import com.arkivanov.decompose.ComponentContext
import com.mindovercnc.linuxcnc.domain.*
import com.mindovercnc.linuxcnc.numpad.NumPadState
import com.mindovercnc.linuxcnc.numpad.data.InputType
import com.mindovercnc.linuxcnc.screen.BaseScreenModel
import com.mindovercnc.model.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.instance

class ManualTurningScreenModel(
    di: DI,
    componentContext: ComponentContext,
) :
    BaseScreenModel<ManualTurningState>(ManualTurningState(), componentContext),
    ManualTurningComponent {
    private val manualPositionUseCase: ManualPositionUseCase by di.instance()
    private val toolsUseCase: ToolsUseCase by di.instance()
    private val offsetsUseCase: OffsetsUseCase by di.instance()
    private val virtualLimitsUseCase: VirtualLimitsUseCase by di.instance()

    init {
        val spindleUseCase: SpindleUseCase by di.instance()
        val feedUseCase: FeedUseCase by di.instance()
        val taperUseCase: TaperUseCase by di.instance()
        val handWheelsUseCase: HandWheelsUseCase by di.instance()
        val simpleCyclesUseCase: SimpleCyclesUseCase by di.instance()

        virtualLimitsUseCase.hasToolLoaded
            .distinctUntilChanged()
            .onEach { limitsAvailable ->
                mutableState.update { it.copy(virtualLimitsAvailable = limitsAvailable) }
            }
            .launchIn(coroutineScope)

        virtualLimits()
            .onEach { limits -> mutableState.update { it.copy(virtualLimitsUiModel = limits) } }
            .launchIn(coroutineScope)

        spindleUseCase
            .spindleFlow()
            .onEach { model -> mutableState.update { it.copy(spindleUiModel = model) } }
            .launchIn(coroutineScope)

        feedUseCase
            .feedFlow()
            .onEach { model -> mutableState.update { it.copy(feedUiModel = model) } }
            .launchIn(coroutineScope)

        taperUseCase
            .taperAngleFlow()
            .onEach { angle -> mutableState.update { it.copy(taperTurningAngle = angle) } }
            .launchIn(coroutineScope)

        handWheelsUseCase.handWheelsUiModel
            .onEach { uiModel -> mutableState.update { it.copy(handWheelsUiModel = uiModel) } }
            .launchIn(coroutineScope)

        combine(offsetsUseCase.getOffsets(), offsetsUseCase.currentWcs) { offsets, current ->
                WcsUiModel(
                    activeOffset = current,
                    wcsOffsets =
                        offsets.map { WcsOffset(it.coordinateSystem, it.xOffset, it.zOffset) }
                )
            }
            .onEach { uiModel -> mutableState.update { it.copy(wcsUiModel = uiModel) } }
            .launchIn(coroutineScope)

        manualPositionUseCase
            .getPositionModel()
            .map {
                val xCoordinateUiModel =
                    CoordinateUiModel(
                        axis = CoordinateAxis.X,
                        primaryValue = it.xAxisPos.primaryValue,
                        secondaryValue = it.xAxisPos.secondaryValue,
                    )
                val zCoordinateUiModel =
                    CoordinateUiModel(
                        axis = CoordinateAxis.Z,
                        primaryValue = it.zAxisPos.primaryValue,
                        secondaryValue = it.zAxisPos.secondaryValue,
                    )
                Pair(xCoordinateUiModel, zCoordinateUiModel)
            }
            .onEach {
                mutableState.update { currentState ->
                    currentState.copy(
                        axisCoordinates =
                            currentState.axisCoordinates.copy(x = it.first, z = it.second)
                    )
                }
            }
            .launchIn(coroutineScope)

        simpleCyclesUseCase.simpleCycleParameters
            .onEach { cycleParams ->
                mutableState.update {
                    it.copy(
                        simpleCycleUiModel =
                            when {
                                cycleParams != null -> SimpleCycleUiModel(cycleParams)
                                else -> null
                            }
                    )
                }
            }
            .launchIn(coroutineScope)
    }

    override fun setWcsSheetVisible(visible: Boolean) {
        mutableState.update { it.copy(showWcsSheet = visible) }
    }

    override fun setSimpleCyclesDrawerOpen(open: Boolean) {
        mutableState.update { it.copy(simpleCyclesDrawerOpen = open) }
    }

    override fun setTaperTurningActive(active: Boolean) {
        mutableState.update { it.copy(taperTurningActive = active) }
    }

    override fun setVirtualLimitsActive(active: Boolean) {
        coroutineScope.launch { virtualLimitsUseCase.setLimitsActive(active) }
    }

    override fun setActiveWcs(wcs: String) {
        coroutineScope.launch { offsetsUseCase.setActiveOffset(wcs) }
    }

    override fun setWorkpieceZ(coordinate: Double) {
        coroutineScope.launch { offsetsUseCase.touchOffZ(coordinate) }
    }

    override fun setToolOffsetX(coordinate: Double) {
        coroutineScope.launch { toolsUseCase.toolTouchOffX(coordinate) }
    }

    override fun setToolOffsetZ(coordinate: Double) {
        coroutineScope.launch { toolsUseCase.toolTouchOffZ(coordinate) }
    }

    override fun setZeroPosX() {
        coroutineScope.launch { manualPositionUseCase.setZeroPosX() }
    }

    override fun setZeroPosZ() {
        coroutineScope.launch { manualPositionUseCase.setZeroPosZ() }
    }

    override fun toggleXAbsRel() {
        manualPositionUseCase.toggleXAbsRel()
    }

    override fun toggleZAbsRel() {
        manualPositionUseCase.toggleZAbsRel()
    }

    override fun openNumPad(inputType: InputType, onSubmitAction: (Double) -> Unit) {
        mutableState.update {
            it.copy(
                numPadState =
                    NumPadState(
                        numInputParameters = inputType,
                        inputType = inputType,
                        onSubmitAction = onSubmitAction
                    )
            )
        }
    }

    override fun closeNumPad() {
        mutableState.update { it.copy(numPadState = null) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun virtualLimits(): Flow<VirtualLimitsUiModel?> {
        return virtualLimitsUseCase.isLimitsActive().flatMapLatest {
            when {
                it ->
                    virtualLimitsUseCase.getSavedVirtualLimits().map { activeLimits ->
                        VirtualLimitsUiModel(
                            xMinus = if (activeLimits.xMinusActive) activeLimits.xMinus else null,
                            xPlus = if (activeLimits.xPlusActive) activeLimits.xPlus else null,
                            zMinus = if (activeLimits.zMinusActive) activeLimits.zMinus else null,
                            zPlus = if (activeLimits.zPlusActive) activeLimits.zPlus else null,
                        )
                    }
                else -> flowOf(null)
            }
        }
    }
}
