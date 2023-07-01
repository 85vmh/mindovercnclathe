package ui.screen.manual.root

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.*
import canvas.*
import org.jetbrains.skia.Paint

@Composable
fun JoystickStatus(
    modifier: Modifier = Modifier.size(80.dp),
    isTaper: Boolean
) {
    val defaultColor = Color.DarkGray
    val centerRadius = 10f
    val outerRadius = 40f

    val axisAngle: Float by animateFloatAsState(
        targetValue = if (isTaper) 45f else 0f,
        animationSpec = tween(
            durationMillis = 100,
            easing = LinearEasing
        )
    )

    Canvas(modifier = modifier) {
        drawCircle(
            center = center,
            radius = centerRadius,
            color = defaultColor
        )

        drawCircle(
            center = center,
            radius = outerRadius,
            color = defaultColor.copy(alpha = 0.2f),
            style = Stroke(width = 1f)
        )

        Direction.values().forEach {
            AxisDirectionActor(
                direction = it,
                centerPoint = center,
                length = outerRadius,
                axisColor = defaultColor
            )
                .rotateBy(angle = axisAngle, pivot = center)
                .drawInto(this)
        }

        AxisTextActor(
            centerPoint = center,
            axisColor = defaultColor,
            lineDrawRadius = 40f,
            textDrawRadius = 45f
        ).drawInto(this)
    }
}

private enum class Direction(val text: String) {
    XPlus("X+"), XMinus("X-"), ZPlus("Z+"), ZMinus("Z-")
}

private class AxisTextActor(
    centerPoint: Offset,
    axisColor: Color,
    textDrawRadius: Float,
    lineDrawRadius: Float,
) : CanvasActor {

    val actors = mutableListOf<CanvasActor>()

    init {
        val textPaint = Paint().apply {
            color = axisColor.toArgb()
        }

        Direction.values().forEach {
            val lineEndPoint = when (it) {
                Direction.XMinus -> Offset(centerPoint.x, centerPoint.y - lineDrawRadius)
                Direction.XPlus -> Offset(centerPoint.x, centerPoint.y + lineDrawRadius)
                Direction.ZMinus -> Offset(centerPoint.x - lineDrawRadius, centerPoint.y)
                Direction.ZPlus -> Offset(centerPoint.x + lineDrawRadius, centerPoint.y)
            }

            val textEndPoint = when (it) {
                Direction.XMinus -> Offset(centerPoint.x, centerPoint.y - textDrawRadius)
                Direction.XPlus -> Offset(centerPoint.x, centerPoint.y + textDrawRadius)
                Direction.ZMinus -> Offset(centerPoint.x - textDrawRadius, centerPoint.y)
                Direction.ZPlus -> Offset(centerPoint.x + textDrawRadius, centerPoint.y)
            }

            val offsetPlacement = when (it) {
                Direction.XMinus -> OffsetPlacement.BottomCenter
                Direction.XPlus -> OffsetPlacement.TopCenter
                Direction.ZMinus -> OffsetPlacement.EndCenter
                Direction.ZPlus -> OffsetPlacement.StartCenter
            }

            actors.add(
                LineActor(
                    start = centerPoint,
                    end = lineEndPoint,
                    thickness = 0.3f,
                    color = axisColor.copy(alpha = 0.7f),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(3f, 3f), 0f),
                    strokeCap = StrokeCap.Round
                )
            )

            actors.add(
                TextActor(
                    text = it.text,
                    offset = textEndPoint,
                    offsetPlacement,
                    paint = textPaint
                )
            )
        }
    }

    override fun drawInto(drawScope: DrawScope) {
        actors.forEach { it.drawInto(drawScope) }
    }
}

private class AxisDirectionActor(
    direction: Direction,
    centerPoint: Offset,
    length: Float,
    thickness: Float = 1f,
    axisColor: Color,
    pathEffect: PathEffect? = null,
) : CanvasActor {

    private val actors = mutableListOf<CanvasActor>()

    init {
        val endPoint = when (direction) {
            Direction.XMinus -> Offset(centerPoint.x, centerPoint.y - length)
            Direction.XPlus -> Offset(centerPoint.x, centerPoint.y + length)
            Direction.ZMinus -> Offset(centerPoint.x - length, centerPoint.y)
            Direction.ZPlus -> Offset(centerPoint.x + length, centerPoint.y)
        }

        val tipAngle = when (direction) {
            Direction.XMinus -> 0f
            Direction.XPlus -> 180f
            Direction.ZMinus -> 270f
            Direction.ZPlus -> 90f
        }

        actors.add(
            LineActor(
                start = centerPoint,
                end = endPoint,
                thickness = thickness,
                color = axisColor,
                pathEffect = pathEffect,
                strokeCap = StrokeCap.Round
            )
        )
        actors.add(
            ArrowTipActor(
                tipOffset = endPoint,
                color = axisColor
            ).rotateBy(angle = tipAngle, pivot = endPoint)
        )
    }

    override fun drawInto(drawScope: DrawScope) {
        actors.forEach { it.drawInto(drawScope) }
    }
}
