package canvas

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import com.mindovercnc.model.PathElement
import java.awt.geom.Point2D
import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.sqrt

fun Path.addLine(lineElement: PathElement.Line, scale: Float = 1f) {
    val start = lineElement.startPoint.toOffset(scale)
    val end = lineElement.endPoint.toOffset(scale)
    moveTo(start.x, start.y)
    lineTo(end.x, end.y)
}

fun Path.addArc(arcElement: PathElement.Arc, scale: Float = 1f) {
    val start = arcElement.startPoint.toOffset(scale)
    val end = arcElement.endPoint.toOffset(scale)
    val center = arcElement.centerPoint.toOffset(scale)
    val direction = if (arcElement.direction == PathElement.Arc.Direction.Clockwise) 1 else -1

    val radiusLength = center.distanceTo(end)
    val referencePoint = Offset(center.x + radiusLength.toFloat(), center.y)
    val startAngle = angleBetweenThreePoints(referencePoint, center, start)
    val endAngle = angleBetweenThreePoints(referencePoint, center, end)

//    val arcAngle = angleBetweenThreePoints(start, center, end)
//    println("Arc angle is: $arcAngle")
//    println("Start angle is: $startAngle")
//    println("End angle is: $endAngle")

    addArc(
        oval = Rect(center, radiusLength.toFloat()),
        startAngleDegrees = startAngle.toFloat() * direction,
        sweepAngleDegrees = (endAngle - startAngle).toFloat() * direction
    )
}

private fun angleBetweenThreePoints(start: Offset, center: Offset, end: Offset): Double {
    val ab: Double = sqrt((start.x - center.x).toDouble().pow(2.0) + (start.y - center.y).toDouble().pow(2.0))
    val ac: Double = sqrt((start.x - end.x).toDouble().pow(2.0) + (start.y - end.y).toDouble().pow(2.0))
    val bc: Double = sqrt((center.x - end.x).toDouble().pow(2.0) + (center.y - end.y).toDouble().pow(2.0))
    val cosValue = (ab * ab + bc * bc - ac * ac) / (2 * bc * ab)
    return acos(cosValue) * (180 / Math.PI)
}

private fun Offset.distanceTo(point: Offset) = Point2D.distance(x.toDouble(), y.toDouble(), point.x.toDouble(), point.y.toDouble())