package com.mindovercnc.model

data class PositionModel(
    val xAxisPos: AxisPosition,
    val zAxisPos: AxisPosition,
    val isDiameterMode: Boolean,
)
