package actor

import androidx.compose.ui.geometry.Offset
import org.jetbrains.skia.Point

fun Point.toOffset() = Offset(x, y)

operator fun Point.minus(point: Point) = Point(x = x - point.x, y = y - point.y)
