package actor


sealed class PathElement(
    open val startPoint: Point2D,
    open val endPoint: Point2D,
) {
    data class Line(
        override val startPoint: Point2D,
        override val endPoint: Point2D,
        val type: Type
    ) : PathElement(startPoint, endPoint) {
        enum class Type {
            Traverse, Feed
        }
    }

    data class Arc(
        override val startPoint: Point2D,
        override val endPoint: Point2D,
        val centerPoint: Point2D,
        val direction: Direction
    ) : PathElement(
        startPoint, endPoint
    ) {
        enum class Direction {
            Clockwise, CounterClockwise
        }
    }
}