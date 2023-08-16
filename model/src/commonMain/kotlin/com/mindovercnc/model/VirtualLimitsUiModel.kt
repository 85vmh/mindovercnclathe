package com.mindovercnc.model

data class VirtualLimitsUiModel(
    val xMinus: Double? = null,
    val xPlus: Double? = null,
    val zMinus: Double? = null,
    val zPlus: Double? = null,
    val zPlusIsTailstockLimit: Boolean = false,
)
