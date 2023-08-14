package codegen

import com.mindovercnc.linuxcnc.format.stripZeros
import com.mindovercnc.linuxcnc.format.toRadians
import com.mindovercnc.model.Axis
import com.mindovercnc.model.Direction
import com.mindovercnc.model.G53AxisLimits
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
        startPoint: Point,
        angle: Double
    ): String {
        // println("----G53 Axis Limits: $limits")
        val cornerPoint =
            when (axis) {
                Axis.X ->
                    when (feedDirection) {
                        Direction.Positive -> Point(limits.xMaxLimit!! * 2, limits.zMinLimit!!)
                        Direction.Negative -> Point(limits.xMinLimit!! * 2, limits.zMaxLimit!!)
                    }
                Axis.Z ->
                    when (feedDirection) {
                        Direction.Positive -> Point(limits.xMaxLimit!! * 2, limits.zMaxLimit!!)
                        Direction.Negative -> Point(limits.xMinLimit!! * 2, limits.zMinLimit!!)
                    }
            }

        println("---Start point: $startPoint")
        println("---Corner point: $cornerPoint")
        val destPoint = computeDestinationPoint(startPoint, cornerPoint, angle)
        println("---End point: $destPoint")
        return "G53 G1 X${destPoint.x.stripZeros(4)} Z${destPoint.z.stripZeros(4)}"
    }

    private fun computeDestinationPoint(
        startPoint: Point,
        cornerPoint: Point,
        angle: Double
    ): Point {
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
            Point(destPointX, cornerPoint.z)
        } else {
            val sign = if (cornerPoint.z > 0) 1 else -1
            Point(cornerPoint.x, startPoint.z + (adjacent * sign))
        }
    }
}
