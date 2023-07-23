package actor

import androidx.compose.ui.graphics.Path
import kotlin.math.abs
import kotlin.math.sin
import kotlin.math.tan
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect

sealed class InsertShape {
    abstract val path: Path

    data class Rhomb(val angle: Int, val height: Float) : InsertShape() {
        private val sideLength = height / abs(sin(Math.toRadians(angle.toDouble()))).toFloat() //hypotenuse
        private val offsetLength = height / abs(tan(Math.toRadians(angle.toDouble()))).toFloat() //adjacent

        override val path: Path
            get() = Path().apply {
                moveTo(x = 0f, y = 0f)
                lineTo(x = sideLength, y = 0f)
                lineTo(x = sideLength + offsetLength, y = height)
                lineTo(x = offsetLength, y = height)
                close()
            }
    }

    data class Triangle(val height: Float) : InsertShape() {
        private val eqTriangleSide = height / (sin(Math.toRadians(60.0))).toFloat()

        override val path: Path
            get() = Path().apply {
                moveTo(x = 0f, y = 0f)
                lineTo(x = -eqTriangleSide / 2, y = height)
                lineTo(x = eqTriangleSide / 2, y = height)
                close()
            }
    }

    data class Circle(val radius: Float) : InsertShape() {
        override val path: Path
            get() = Path().apply {
                addOval(Rect(Offset.Zero, radius))
            }
    }

    data class Drill(
        val diameter: Float,
        val visibleLength: Float = diameter * 3,
        val tipAngle: Float = 118f
    ) : InsertShape() {

        val radius = diameter / 2
        private val adjacentLength = radius / abs(tan(Math.toRadians(tipAngle / 2.toDouble()))).toFloat()

        override val path: Path
            get() = Path().apply {
                moveTo(x = 0f, y = 0f)
                lineTo(x = adjacentLength, y = -radius)
                lineTo(x = visibleLength - adjacentLength, y = -radius)
                lineTo(x = visibleLength - adjacentLength, y = radius)
                lineTo(x = adjacentLength, y = radius)
                close()
            }

    }
}