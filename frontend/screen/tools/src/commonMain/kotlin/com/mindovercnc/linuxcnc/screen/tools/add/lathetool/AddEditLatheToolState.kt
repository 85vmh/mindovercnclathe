package com.mindovercnc.linuxcnc.screen.tools.add.lathetool

import com.mindovercnc.linuxcnc.tools.model.CuttingInsert
import com.mindovercnc.linuxcnc.tools.model.ToolType
import com.mindovercnc.model.SpindleDirection
import com.mindovercnc.model.TipOrientation

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