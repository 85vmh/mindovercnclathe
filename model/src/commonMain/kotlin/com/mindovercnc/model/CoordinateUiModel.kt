package com.mindovercnc.model

data class CoordinateUiModel(
    val axis: CoordinateAxis,
    val primaryValue: Double,
    val secondaryValue: Double? = null,
    val isIncremental: Boolean = secondaryValue != null,
    val units: String = "mm",
    val displayDigits: Int = 3,
)
