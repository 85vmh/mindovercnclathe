package com.mindovercnc.model

data class SpindleUiModel(
    val isRpmMode: Boolean = true,
    val spindleOverride: Double = 0.0,
    val setRpm: Int? = null,
    val setCss: Int? = null,
    val actualRpm: Int = 0,
    val maxRpm: Int? = null,
    val stopAngle: Double? = null,
)
