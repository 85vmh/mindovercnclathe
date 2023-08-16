package com.mindovercnc.linuxcnc.domain.model

data class OffsetEntry(
    val coordinateSystem: String,
    val xOffset: Double,
    val zOffset: Double
)
