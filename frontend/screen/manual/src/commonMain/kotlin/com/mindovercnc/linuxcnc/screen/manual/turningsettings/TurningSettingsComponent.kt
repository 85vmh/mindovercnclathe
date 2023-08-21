package com.mindovercnc.linuxcnc.screen.manual.turningsettings

import com.mindovercnc.linuxcnc.screen.AppScreenComponent

interface TurningSettingsComponent : AppScreenComponent<TurningSettingsState> {
    fun setRpmValue(value: Int)
    fun setCssValue(value: Int)
    fun setMaxSpeedValue(value: Int)
    fun setOrientAngle(value: Double)
    fun setUnitsPerRev(value: Double)
    fun setUnitsPerMin(value: Double)
    fun setRpmActive(value: Boolean)
    fun setUnitsPerRevActive(value: Boolean)
    fun setOrientActive(value: Boolean)
    fun applyChanges()
}