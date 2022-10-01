package com.mindovercnc.model

sealed class LatheTool(
    open val toolId: Int?,
    open val tipOrientation: TipOrientation,
    open val spindleDirection: SpindleDirection,
    open val secondsUsed: Double
) {
    data class Turning(
        override val toolId: Int? = null,
        val insert: CuttingInsert,
        override val tipOrientation: TipOrientation,
        val frontAngle: Double,
        val backAngle: Double,
        override val spindleDirection: SpindleDirection,
        override val secondsUsed: Double = 0.0
    ) : LatheTool(toolId, tipOrientation, spindleDirection, secondsUsed)

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
    ) : LatheTool(toolId, tipOrientation, spindleDirection, secondsUsed)

    data class Drilling(
        override val toolId: Int? = null,
        val insert: CuttingInsert? = null,
        val toolDiameter: Double,
        val maxZDepth: Double,
        override val secondsUsed: Double = 0.0
    ) : LatheTool(toolId, TipOrientation.Position7, SpindleDirection.Reverse, secondsUsed)

    data class Reaming(
        override val toolId: Int? = null,
        val insert: CuttingInsert? = null,
        val toolDiameter: Double,
        val maxZDepth: Double,
        override val secondsUsed: Double = 0.0
    ) : LatheTool(toolId, TipOrientation.Position7, SpindleDirection.Reverse, secondsUsed)

    data class Parting(
        override val toolId: Int? = null,
        val insert: CuttingInsert,
        val bladeWidth: Double,
        val maxXDepth: Double,
        override val secondsUsed: Double = 0.0
    ) : LatheTool(toolId, TipOrientation.Position6, SpindleDirection.Reverse, secondsUsed)

    data class Grooving(
        override val toolId: Int? = null,
        val insert: CuttingInsert,
        override val tipOrientation: TipOrientation,
        override val spindleDirection: SpindleDirection,
        val bladeWidth: Double,
        val maxXDepth: Double,
        override val secondsUsed: Double = 0.0
    ) : LatheTool(toolId, tipOrientation, spindleDirection, secondsUsed)

    data class OdThreading(
        override val toolId: Int? = null,
        val insert: CuttingInsert,
        val minPitch: Double,
        val maxPitch: Double,
        override val secondsUsed: Double = 0.0
    ) : LatheTool(toolId, TipOrientation.Position6, SpindleDirection.Reverse, secondsUsed)

    data class IdThreading(
        override val toolId: Int? = null,
        val insert: CuttingInsert,
        val minPitch: Double,
        val maxPitch: Double,
        val maxZDepth: Double? = null,
        override val secondsUsed: Double = 0.0
    ) : LatheTool(toolId, TipOrientation.Position8, SpindleDirection.Reverse, secondsUsed)

    data class Slotting(
        override val toolId: Int? = null,
        val insert: CuttingInsert? = null,
        val bladeWidth: Double,
        val maxZDepth: Double,
        override val secondsUsed: Double = 0.0
    ) : LatheTool(toolId, TipOrientation.Position7, SpindleDirection.None, secondsUsed)
}
