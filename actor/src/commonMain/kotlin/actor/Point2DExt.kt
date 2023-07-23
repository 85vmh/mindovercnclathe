package actor

import androidx.compose.ui.geometry.Offset

fun Point2D.toOffset(multiplicationFactor: Float = 1f) = Offset(
    x = (z * multiplicationFactor).toFloat(),
    y = (x * multiplicationFactor).toFloat()
)

operator fun Point2D.plus(point: Point2D): Point2D {
    return Point2D(
        x = x.plus(point.x),
        z = z.plus(point.z)
    )
}

operator fun Point2D.minus(point: Point2D): Point2D {
    return Point2D(
        x = x.minus(point.x),
        z = z.minus(point.z)
    )
}
