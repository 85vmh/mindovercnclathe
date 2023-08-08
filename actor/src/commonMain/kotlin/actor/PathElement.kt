package actor

import org.jetbrains.skia.Point


sealed class PathElement(
    open val startPoint: Point,
    open val endPoint: Point,
) {
    data class Line(
        override val startPoint: Point,
        override val endPoint: Point,
        val type: Type
    ) : PathElement(startPoint, endPoint) {
        enum class Type {
            Traverse, Feed
        }
    }

    data class Arc(
        override val startPoint: Point,
        override val endPoint: Point,
        val centerPoint: Point,
        val direction: Direction
    ) : PathElement(
        startPoint, endPoint
    ) {
        enum class Direction {
            Clockwise, CounterClockwise
        }
    }
}