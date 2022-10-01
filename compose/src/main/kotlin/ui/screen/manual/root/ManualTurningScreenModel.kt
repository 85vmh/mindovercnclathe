package ui.screen.manual.root

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import screen.composables.NumPadState
import screen.uimodel.InputType
import screen.uimodel.NumericInputs
import usecase.*

class ManualTurningScreenModel(
    spindleUseCase: SpindleUseCase,
    feedUseCase: FeedUseCase,
    taperUseCase: TaperUseCase,
    handWheelsUseCase: HandWheelsUseCase,
    private val manualPositionUseCase: ManualPositionUseCase,
    private val toolsUseCase: ToolsUseCase,
    private val offsetsUseCase: OffsetsUseCase,
    private val virtualLimitsUseCase: VirtualLimitsUseCase,
    simpleCyclesUseCase: SimpleCyclesUseCase
) : StateScreenModel<ManualTurningScreenModel.State>(State()) {

    data class State(
        val xCoordinateUiModel: CoordinateUiModel = CoordinateUiModel(CoordinateUiModel.Axis.X, 0.0),
        val zCoordinateUiModel: CoordinateUiModel = CoordinateUiModel(CoordinateUiModel.Axis.Z, 0.0),
        val toolLoaded: Int? = null,
        val spindleUiModel: SpindleUiModel? = null,
        val feedUiModel: FeedUiModel? = null,
        val taperTurningActive: Boolean = false,
        val taperTurningAngle: Double = 45.0,
        val virtualLimitsAvailable: Boolean = false,
        val virtualLimitsUiModel: VirtualLimitsUiModel? = null,
        val handWheelsUiModel: HandWheelsUiModel? = null,
        val simpleCycleUiModel: SimpleCycleUiModel? = null,
        val wcsUiModel: WcsUiModel? = null,
        val numPadState: NumPadState? = null
    )

    init {
        virtualLimitsUseCase.hasToolLoaded
            .distinctUntilChanged()
            .onEach { limitsAvailable ->
                mutableState.update {
                    it.copy(virtualLimitsAvailable = limitsAvailable)
                }
            }.launchIn(coroutineScope)

        virtualLimits().onEach { limits ->
            mutableState.update {
                it.copy(virtualLimitsUiModel = limits)
            }
        }.launchIn(coroutineScope)

        spindleUseCase.spindleFlow()
            .onEach { model ->
                mutableState.update {
                    it.copy(spindleUiModel = model)
                }
            }.launchIn(coroutineScope)

        feedUseCase.feedFlow()
            .onEach { model ->
                mutableState.update {
                    it.copy(feedUiModel = model)
                }
            }.launchIn(coroutineScope)

        taperUseCase.taperAngleFlow()
            .onEach { angle ->
                mutableState.update {
                    it.copy(taperTurningAngle = angle)
                }
            }.launchIn(coroutineScope)

        handWheelsUseCase.handWheelsUiModel
            .onEach { uiModel ->
                mutableState.update {
                    it.copy(handWheelsUiModel = uiModel)
                }
            }.launchIn(coroutineScope)

        combine(
            offsetsUseCase.getOffsets(),
            offsetsUseCase.currentWcs
        ) { offsets, current ->
            WcsUiModel(
                activeOffset = current,
                wcsOffsets = offsets.map { WcsOffset(it.coordinateSystem, it.xOffset, it.zOffset) }
            )
        }.onEach { uiModel ->
            mutableState.update {
                it.copy(wcsUiModel = uiModel)
            }
        }.launchIn(coroutineScope)

        manualPositionUseCase.getPositionModel()
            .map {
                val xCoordinateUiModel = CoordinateUiModel(
                    axis = CoordinateUiModel.Axis.X,
                    primaryValue = it.xAxisPos.primaryValue,
                    secondaryValue = it.xAxisPos.secondaryValue,
                )
                val zCoordinateUiModel = CoordinateUiModel(
                    axis = CoordinateUiModel.Axis.Z,
                    primaryValue = it.zAxisPos.primaryValue,
                    secondaryValue = it.zAxisPos.secondaryValue,
                )
                Pair(xCoordinateUiModel, zCoordinateUiModel)
            }
            .onEach {
                mutableState.update { currentState ->
                    currentState.copy(xCoordinateUiModel = it.first, zCoordinateUiModel = it.second)
                }
            }.launchIn(coroutineScope)

        simpleCyclesUseCase.simpleCycleParameters
            .onEach { cycleParams ->
                mutableState.update {
                    it.copy(
                        simpleCycleUiModel = when {
                            cycleParams != null -> SimpleCycleUiModel(cycleParams)
                            else -> null
                        }
                    )
                }
            }.launchIn(coroutineScope)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun virtualLimits(): Flow<VirtualLimitsUiModel?> {
        return virtualLimitsUseCase.isLimitsActive()
            .flatMapLatest {
                when {
                    it -> virtualLimitsUseCase.getSavedVirtualLimits()
                        .map { activeLimits ->
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

    fun setTaperTurningActive(active: Boolean) {
        mutableState.update {
            it.copy(taperTurningActive = active)
        }
    }

    fun setVirtualLimitsActive(active: Boolean) {
        coroutineScope.launch {
            virtualLimitsUseCase.setLimitsActive(active)
        }
    }

    fun setActiveWcs(wcs: String) {
        coroutineScope.launch {
            offsetsUseCase.setActiveOffset(wcs)
        }
    }

    fun setWorkpieceZ(coordinate: Double) {
        coroutineScope.launch {
            offsetsUseCase.touchOffZ(coordinate)
        }
    }

    fun setToolOffsetX(coordinate: Double) {
        coroutineScope.launch {
            toolsUseCase.toolTouchOffX(coordinate)
        }
    }

    fun setToolOffsetZ(coordinate: Double) {
        coroutineScope.launch {
            toolsUseCase.toolTouchOffZ(coordinate)
        }
    }

    fun setZeroPosX() {
        coroutineScope.launch {
            manualPositionUseCase.setZeroPosX()
        }
    }

    fun setZeroPosZ() {
        coroutineScope.launch {
            manualPositionUseCase.setZeroPosZ()
        }
    }

    fun toggleXAbsRel() {
        manualPositionUseCase.toggleXAbsRel()
    }

    fun toggleZAbsRel() {
        manualPositionUseCase.toggleZAbsRel()
    }

    fun openNumPad(inputType: InputType, onSubmitAction: (Double) -> Unit) {
        mutableState.update {
            it.copy(
                numPadState = NumPadState(
                    numInputParameters = NumericInputs.entries[inputType]!!,
                    inputType = inputType,
                    onSubmitAction = onSubmitAction
                )
            )
        }
    }

    fun closeNumPad() {
        mutableState.update {
            it.copy(numPadState = null)
        }
    }
}

