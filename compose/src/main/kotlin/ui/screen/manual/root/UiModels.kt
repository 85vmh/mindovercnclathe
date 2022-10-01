package ui.screen.manual.root

import usecase.model.SimpleCycleParameters

data class CoordinateUiModel(
    val axis: Axis,
    val primaryValue: Double,
    val secondaryValue: Double? = null,
    val isIncremental: Boolean = false,
    val units: String = "mm",
    val displayDigits: Int = 3
) {
    enum class Axis {
        X, Z
    }
}

data class SpindleUiModel(
    val isRpmMode: Boolean = true,
    val spindleOverride: Int = 0,
    val setRpm: Int? = null,
    val setCss: Int? = null,
    val actualRpm: Int = 0,
    val maxRpm: Int? = null,
    val stopAngle: Double? = null
)

data class FeedUiModel(
    val isUnitsPerRevMode: Boolean = true,
    val feedOverride: Double = 0.0,
    val setFeed: Double = 0.0,
    val actualFeed: Double = 0.0,
    val units: String = "mm"
)

data class VirtualLimitsUiModel(
    val xMinus: Double? = null,
    val xPlus: Double? = null,
    val zMinus: Double? = null,
    val zPlus: Double? = null,
    val zPlusIsTailstockLimit: Boolean = false
)

data class HandWheelsUiModel(
    val active: Boolean,
    val increment: Float,
    val units: String = "mm"
)

data class WcsUiModel(
    val activeOffset: String,
    val wcsOffsets: List<WcsOffset>
) {
    val selected = wcsOffsets.firstOrNull { it.coordinateSystem == activeOffset }
}

data class WcsOffset(
    val coordinateSystem: String,
    val xOffset: Double,
    val zOffset: Double
)

data class SimpleCycleUiModel(
    val simpleCycleParameters: SimpleCycleParameters
)