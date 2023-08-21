package com.mindovercnc.linuxcnc.screen.manual.turningsettings

data class TurningSettingsState(
    val rpmValue: Int = 0,
    val cssValue: Int = 0,
    val maxSpeed: Int = 0,
    val orientAngle: Double = 0.0,
    val isOrientActive: Boolean = false,
    val unitsPerRevValue: Double = 0.0,
    val unitsPerMinValue: Double = 0.0,
    val isRpmActive: Boolean = true,
    val isUnitPerRevActive: Boolean = true,
    val cssUnit: String = "m/min",
    val feedUnit: String = "mm",
)