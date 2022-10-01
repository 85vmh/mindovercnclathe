package com.mindovercnc.model

/**
 * This represents the limits of the current work coordinate system.
 * Basically this, it's just the machine limits, on which there is applied the offset of the current WCS.
 */
data class WcsLimits(
    val xMin: Double = 0.0,
    val xMax: Double = 0.0,
    val zMin: Double = 0.0,
    val zMax: Double = 0.0
)