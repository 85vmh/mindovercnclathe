package com.mindovercnc.linuxcnc.domain.model

import androidx.compose.runtime.mutableStateOf
import com.mindovercnc.model.AllowedSpindleDirection
import com.mindovercnc.model.tool.ToolType

class AddEditToolState(
    toolNo: Int,
    toolType: ToolType,
    spindleDirection: AllowedSpindleDirection,
    xOffset: Double,
    zOffset: Double,
    tipRadius: Double,
    bladeWidth: Double,
    diameter: Double,
    orientation: Int
) {
    val toolNo = mutableStateOf(toolNo)
    val toolType = mutableStateOf(toolType)
    val spindleDirection = mutableStateOf(spindleDirection)
    val xOffset = mutableStateOf(xOffset)
    val zOffset = mutableStateOf(zOffset)
    val tipRadius = mutableStateOf(tipRadius)
    val bladeWidth = mutableStateOf(bladeWidth)
    val diameter = mutableStateOf(diameter)
    val orientation = mutableStateOf(orientation)
}