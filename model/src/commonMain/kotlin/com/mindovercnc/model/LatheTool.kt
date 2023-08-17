package com.mindovercnc.model

import com.mindovercnc.model.tool.ToolType

sealed class LatheTool(
    open val toolId: Int?,
    open val tipOrientation: TipOrientation,
    open val spindleDirection: SpindleDirection,
    open val secondsUsed: Double,
    val type: ToolType
) {

    data class Turning(
        override val toolId: Int? = null,
        val insert: CuttingInsert,
        override val tipOrientation: TipOrientation,
        val frontAngle: Double,
        val backAngle: Double,
        override val spindleDirection: SpindleDirection,
        override val secondsUsed: Double = 0.0
    ) : LatheTool(toolId, tipOrientation, spindleDirection, secondsUsed, ToolType.Turning)

    data class Boring(
        override val toolId: Int? = null,
        val insert: CuttingInsert,
        override val tipOrientation: TipOrientation,
        val frontAngle: Double,
        val backAngle: Double,
        override val spindleDirection: SpindleDirection,
        val minBoreDiameter: Double,
        val maxZDepth: Double,
        override val secondsUsed: Double = 0.0
    ) : LatheTool(toolId, tipOrientation, spindleDirection, secondsUsed, ToolType.Boring)

    data class Drilling(
        override val toolId: Int? = null,
        val insert: CuttingInsert? = null,
        val toolDiameter: Double,
        val maxZDepth: Double,
        override val secondsUsed: Double = 0.0
    ) : LatheTool(toolId, TipOrientation.Position7, SpindleDirection.Reverse, secondsUsed, ToolType.Drilling)

    data class Reaming(
        override val toolId: Int? = null,
        val insert: CuttingInsert? = null,
        val toolDiameter: Double,
        val maxZDepth: Double,
        override val secondsUsed: Double = 0.0
    ) : LatheTool(toolId, TipOrientation.Position7, SpindleDirection.Reverse, secondsUsed, ToolType.Reaming)

    data class Parting(
        override val toolId: Int? = null,
        val insert: CuttingInsert,
        val bladeWidth: Double,
        val maxXDepth: Double,
        override val secondsUsed: Double = 0.0
    ) : LatheTool(toolId, TipOrientation.Position6, SpindleDirection.Reverse, secondsUsed, ToolType.Parting)

    data class Grooving(
        override val toolId: Int? = null,
        val insert: CuttingInsert,
        override val tipOrientation: TipOrientation,
        override val spindleDirection: SpindleDirection,
        val bladeWidth: Double,
        val maxXDepth: Double,
        override val secondsUsed: Double = 0.0
    ) : LatheTool(toolId, tipOrientation, spindleDirection, secondsUsed, ToolType.Grooving)

    data class OdThreading(
        override val toolId: Int? = null,
        val insert: CuttingInsert,
        val minPitch: Double,
        val maxPitch: Double,
        override val secondsUsed: Double = 0.0
    ) : LatheTool(toolId, TipOrientation.Position6, SpindleDirection.Reverse, secondsUsed, ToolType.OdThreading)

    data class IdThreading(
        override val toolId: Int? = null,
        val insert: CuttingInsert,
        val minPitch: Double,
        val maxPitch: Double,
        val maxZDepth: Double? = null,
        override val secondsUsed: Double = 0.0
    ) : LatheTool(toolId, TipOrientation.Position8, SpindleDirection.Reverse, secondsUsed, ToolType.IdThreading)

    data class Slotting(
        override val toolId: Int? = null,
        val insert: CuttingInsert? = null,
        val bladeWidth: Double,
        val maxZDepth: Double,
        override val secondsUsed: Double = 0.0
    ) : LatheTool(toolId, TipOrientation.Position7, SpindleDirection.None, secondsUsed, ToolType.Slotting)
}
