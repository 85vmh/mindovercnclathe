package com.mindovercnc.linuxcnc.screen.manual.virtuallimits

data class VirtualLimitsState(
    val xMinus: Double = 0.0,
    val xPlus: Double = 0.0,
    val zMinus: Double = 0.0,
    val zPlus: Double = 0.0,
    val xMinusActive: Boolean = false,
    val xPlusActive: Boolean = false,
    val zMinusActive: Boolean = false,
    val zPlusActive: Boolean = false,
    val zPlusIsToolRelated: Boolean = false
)