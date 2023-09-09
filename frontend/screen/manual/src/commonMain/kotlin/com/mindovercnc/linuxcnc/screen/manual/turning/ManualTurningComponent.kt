package com.mindovercnc.linuxcnc.screen.manual.turning

import com.mindovercnc.linuxcnc.numpad.data.InputType
import com.mindovercnc.linuxcnc.screen.AppScreenComponent

interface ManualTurningComponent : AppScreenComponent<ManualTurningState> {
    fun setWcsSheetVisible(visible: Boolean)
    fun setSimpleCyclesDrawerOpen(open: Boolean)

    fun setTaperTurningActive(active: Boolean)
    fun setVirtualLimitsActive(active: Boolean)
    fun setActiveWcs(wcs: String)
    fun setWorkpieceZ(coordinate: Double)
    fun setToolOffsetX(coordinate: Double)
    fun setToolOffsetZ(coordinate: Double)
    fun setZeroPosX()
    fun setZeroPosZ()
    fun toggleXAbsRel()
    fun toggleZAbsRel()
    fun openNumPad(inputType: InputType, onSubmitAction: (Double) -> Unit)
    fun closeNumPad()
}
