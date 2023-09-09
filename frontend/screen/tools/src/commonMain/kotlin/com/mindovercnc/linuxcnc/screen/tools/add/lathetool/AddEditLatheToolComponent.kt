package com.mindovercnc.linuxcnc.screen.tools.add.lathetool

import com.mindovercnc.linuxcnc.screen.AppScreenComponent
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert
import com.mindovercnc.linuxcnc.tools.model.LatheTool
import com.mindovercnc.linuxcnc.tools.model.ToolType
import com.mindovercnc.model.SpindleDirection
import com.mindovercnc.model.TipOrientation

interface AddEditLatheToolComponent : AppScreenComponent<AddEditLatheToolState> {
    val latheTool: LatheTool?

    fun setToolId(value: Int)
    fun setToolType(value: ToolType)
    fun setCuttingInsert(value: CuttingInsert)
    fun setToolOrientation(orientation: TipOrientation)
    fun setFrontAngle(value: Int)
    fun setBackAngle(value: Int)
    fun setSpindleDirection(value: SpindleDirection)
    fun setMinBoreDiameter(value: Double)
    fun setBladeWidth(value: Double)
    fun setMaxXDepth(value: Double)
    fun setMaxZDepth(value: Double)
    fun setToolDiameter(value: Double)
    fun setMinThreadPitch(value: Double)
    fun setMaxThreadPitch(value: Double)
    fun applyChanges()
}