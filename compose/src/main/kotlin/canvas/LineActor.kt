package canvas

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke

open class LineActor(
    private val start: Offset,
    private val end: Offset,
    private val thickness: Float = 1f,
    private val pathEffect: PathEffect? = null,
    private val strokeCap: StrokeCap = Stroke.DefaultCap,
    private val color: Color = Color.Black,
) : CanvasActor {
    override fun drawInto(drawScope: DrawScope) {
        drawScope.drawLine(
            start = start,
            end = end,
            color = color,
            strokeWidth = thickness,
            pathEffect = pathEffect,
            cap = strokeCap
        )
    }
}