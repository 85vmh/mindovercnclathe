package com.mindovercnc.linuxcnc.domain

import com.mindovercnc.linuxcnc.format.stripZeros
import com.mindovercnc.linuxcnc.format.toRadians
import com.mindovercnc.model.Axis
import com.mindovercnc.model.Direction
import com.mindovercnc.model.G53AxisLimits
import com.mindovercnc.model.codegen.CodegenPoint
import kotlin.math.abs
import kotlin.math.tan

object ManualTurningHelper {

    fun getStraightTurningCommand(
        axis: Axis,
        feedDirection: Direction,
        limits: G53AxisLimits
    ): String {
        // println("----G53 Axis Limits: $limits")
        val limit =
            when (axis) {
                Axis.X ->
                    when (feedDirection) {
                        Direction.Negative ->
                            limits.xMinLimit!! * 2 // because lathes work in diameter mode
                        Direction.Positive ->
                            limits.xMaxLimit!! * 2 // because lathes work in diameter mode
                    }
                Axis.Z ->
                    when (feedDirection) {
                        Direction.Negative -> limits.zMinLimit!!
                        Direction.Positive -> limits.zMaxLimit!!
                    }
            }
        return "G53 G1 ${axis.name + limit.stripZeros(4)}"
    }

    fun getTaperTurningCommand(
        axis: Axis,
        feedDirection: Direction,
        limits: G53AxisLimits,
        startPoint: CodegenPoint,
        angle: Double
    ): String {
        // println("----G53 Axis Limits: $limits")
        val cornerPoint =
            when (axis) {
                Axis.X ->
                    when (feedDirection) {
                        Direction.Positive -> CodegenPoint(limits.xMaxLimit!! * 2, limits.zMinLimit!!)
                        Direction.Negative -> CodegenPoint(limits.xMinLimit!! * 2, limits.zMaxLimit!!)
                    }
                Axis.Z ->
                    when (feedDirection) {
                        Direction.Positive -> CodegenPoint(limits.xMaxLimit!! * 2, limits.zMaxLimit!!)
                        Direction.Negative -> CodegenPoint(limits.xMinLimit!! * 2, limits.zMinLimit!!)
                    }
            }

        println("---Start point: $startPoint")
        println("---Corner point: $cornerPoint")
        val destPoint = computeDestinationPoint(startPoint, cornerPoint, angle)
        println("---End point: $destPoint")
        return "G53 G1 X${destPoint.x.stripZeros(4)} Z${destPoint.z.stripZeros(4)}"
    }

    private fun computeDestinationPoint(
        startPoint: CodegenPoint,
        cornerPoint: CodegenPoint,
        angle: Double
    ): CodegenPoint {
        val opposite = abs(cornerPoint.x - startPoint.x)
        val adjacent = (opposite / tan(toRadians(angle))) / 2 // divided by 2 due to diameter mode
        val maxDistZ = abs(cornerPoint.z - startPoint.z)
        return if (adjacent > maxDistZ) {
            val extraDistZ = adjacent - maxDistZ
            val sign = if (cornerPoint.x > 0) -1 else 1
            val smallOpposite = extraDistZ * tan(toRadians(angle))
            val destPointX =
                cornerPoint.x +
                    (2 * smallOpposite * sign) // minus when xMaxLimit, plus when xMinLimit
            CodegenPoint(destPointX, cornerPoint.z)
        } else {
            val sign = if (cornerPoint.z > 0) 1 else -1
            CodegenPoint(cornerPoint.x, startPoint.z + (adjacent * sign))
        }
    }
}
