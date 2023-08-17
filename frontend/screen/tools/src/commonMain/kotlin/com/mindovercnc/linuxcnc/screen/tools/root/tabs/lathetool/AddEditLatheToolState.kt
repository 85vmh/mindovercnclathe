package com.mindovercnc.linuxcnc.screen.tools.root.tabs.lathetool

import com.mindovercnc.model.CuttingInsert
import com.mindovercnc.model.SpindleDirection
import com.mindovercnc.model.TipOrientation
import com.mindovercnc.model.tool.ToolType

data class AddEditLatheToolState(
    val latheToolId: Int? = null,
    val toolTypes: List<ToolType> = ToolType.entries,
    val toolType: ToolType? = null,
    val cuttingInserts: List<CuttingInsert> = emptyList(),
    val cuttingInsert: CuttingInsert? = null,
    val spindleDirection: SpindleDirection? = null,
    val toolOrientation: TipOrientation? = null,
    val frontAngle: Int = 0,
    val backAngle: Int = 0,
    val minBoreDiameter: Double = 0.0,
    val bladeWidth: Double = 0.0,
    val maxZDepth: Double = 0.0,
    val maxXDepth: Double = 0.0,
    val toolDiameter: Double = 0.0,
    val minThreadPitch: Double = 0.0,
    val maxThreadPitch: Double = 0.0,
)
