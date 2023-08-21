package com.mindovercnc.linuxcnc.screen.manual.virtuallimits

import com.mindovercnc.linuxcnc.screen.AppScreenComponent

interface VirtualLimitsComponent : AppScreenComponent<VirtualLimitsState> {
    fun teachInXMinus()
    fun setXMinus(value: Double)
    fun teachInXPlus()
    fun setXPlus(value: Double)
    fun teachInZMinus()
    fun setZMinus(value: Double)
    fun teachInZPlus()
    fun setZPlus(value: Double)
    fun setXMinusActive(value: Boolean)
    fun setXPlusActive(value: Boolean)
    fun setZMinusActive(value: Boolean)
    fun setZPlusActive(value: Boolean)
    fun setZPlusToolRelated(value: Boolean)
    fun applyChanges()
}