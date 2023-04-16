package ui.screen.manual.root

import screen.composables.NumPadState

data class ManualTurningState(
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
