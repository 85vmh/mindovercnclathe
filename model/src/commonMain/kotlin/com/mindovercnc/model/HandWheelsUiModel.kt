package com.mindovercnc.model

data class HandWheelsUiModel(
    val active: Boolean,
    val increment: Float,
    val units: String = "mm",
)
