package com.mindovercnc.linuxcnc.screen.manual.tapersettings

import com.mindovercnc.linuxcnc.screen.AppScreenComponent

interface TaperSettingsComponent : AppScreenComponent<TaperSettingsState> {
    fun setAngle(value: Double)
    fun applyChanges()
}