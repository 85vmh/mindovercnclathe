package com.mindovercnc.data.linuxcnc

import com.mindovercnc.model.G53AxisLimits
import com.mindovercnc.model.IniFile


interface IniFileRepository {
    fun getIniFile(): IniFile

    fun getActiveLimits(): G53AxisLimits

    fun setCustomG53AxisLimits(g53AxisLimits: G53AxisLimits)

    fun toggleCustomLimits()

    fun isCustomLimitsActive(): Boolean
}