package canvas

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.max

class DividerActor(
    val startPoint: Offset,
    val startRadius: Float,
    val endRadius: Float
) : CanvasActor {

    val drawTopLine = LineActor(
        startPoint,
        Offset(x = startPoint.x, y = startPoint.y - max(startRadius, endRadius))
    )

    val drawBottomLine = LineActor(
        Offset(x = startPoint.x, y = startPoint.y + startRadius),
        Offset(x = startPoint.x, y = startPoint.y + endRadius),
    )

    override fun drawInto(drawScope: DrawScope) {
        drawTopLine.drawInto(drawScope)
        drawBottomLine.drawInto(drawScope)
    }
}