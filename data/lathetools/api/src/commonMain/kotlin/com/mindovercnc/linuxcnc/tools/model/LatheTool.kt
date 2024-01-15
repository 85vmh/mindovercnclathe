package com.mindovercnc.linuxcnc.tools.model

import com.mindovercnc.model.SpindleDirection
import com.mindovercnc.model.TipOrientation
import kotlinx.serialization.Serializable

@Serializable
sealed class LatheTool(val type: ToolType) {
    abstract val toolId: Int?
    abstract val tipOrientation: TipOrientation
    abstract val spindleDirection: SpindleDirection
    abstract val secondsUsed: Double

    @Serializable
    data class Turning(
        override val toolId: Int? = null,
        val insert: CuttingInsert,
        override val tipOrientation: TipOrientation,
        val frontAngle: Double,
        val backAngle: Double,
        override val spindleDirection: SpindleDirection,
        override val secondsUsed: Double = 0.0
    ) : LatheTool(ToolType.Turning)

    @Serializable
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
    ) : LatheTool(ToolType.Boring)

    @Serializable
    data class Drilling(
        override val toolId: Int? = null,
        val insert: CuttingInsert? = null,
        val toolDiameter: Double,
        val maxZDepth: Double,
        override val secondsUsed: Double = 0.0
    ) : LatheTool(ToolType.Drilling) {
        override val tipOrientation: TipOrientation
            get() = TipOrientation.Position7

        override val spindleDirection: SpindleDirection
            get() = SpindleDirection.Reverse
    }

    @Serializable
    data class Reaming(
        override val toolId: Int? = null,
        val insert: CuttingInsert? = null,
        val toolDiameter: Double,
        val maxZDepth: Double,
        override val secondsUsed: Double = 0.0
    ) : LatheTool(ToolType.Reaming) {
        override val tipOrientation: TipOrientation
            get() = TipOrientation.Position7

        override val spindleDirection: SpindleDirection
            get() = SpindleDirection.Reverse
    }

    @Serializable
    data class Parting(
        override val toolId: Int? = null,
        val insert: CuttingInsert,
        val bladeWidth: Double,
        val maxXDepth: Double,
        override val secondsUsed: Double = 0.0
    ) : LatheTool(ToolType.Parting) {
        override val tipOrientation: TipOrientation
            get() = TipOrientation.Position6

        override val spindleDirection: SpindleDirection
            get() = SpindleDirection.Reverse
    }

    @Serializable
    data class Grooving(
        override val toolId: Int? = null,
        val insert: CuttingInsert,
        override val tipOrientation: TipOrientation,
        override val spindleDirection: SpindleDirection,
        val bladeWidth: Double,
        val maxXDepth: Double,
        override val secondsUsed: Double = 0.0
    ) : LatheTool(ToolType.Grooving)

    @Serializable
    data class OdThreading(
        override val toolId: Int? = null,
        val insert: CuttingInsert,
        val minPitch: Double,
        val maxPitch: Double,
        override val secondsUsed: Double = 0.0
    ) : LatheTool(ToolType.OdThreading) {
        override val tipOrientation: TipOrientation
            get() = TipOrientation.Position6

        override val spindleDirection: SpindleDirection
            get() = SpindleDirection.Reverse
    }

    @Serializable
    data class IdThreading(
        override val toolId: Int? = null,
        val insert: CuttingInsert,
        val minPitch: Double,
        val maxPitch: Double,
        val maxZDepth: Double? = null,
        override val secondsUsed: Double = 0.0
    ) : LatheTool(ToolType.IdThreading) {
        override val tipOrientation: TipOrientation
            get() = TipOrientation.Position8

        override val spindleDirection: SpindleDirection
            get() = SpindleDirection.Reverse
    }

    @Serializable
    data class Slotting(
        override val toolId: Int? = null,
        val insert: CuttingInsert? = null,
        val bladeWidth: Double,
        val maxZDepth: Double,
        override val secondsUsed: Double = 0.0
    ) : LatheTool(ToolType.Slotting) {
        override val tipOrientation: TipOrientation
            get() = TipOrientation.Position8

        override val spindleDirection: SpindleDirection
            get() = SpindleDirection.Reverse
    }
}
