package canvas

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope

class StartDividerActor(
    val startPoint: Offset,
    val radius: Float
) : CanvasActor {

    val drawStartLine = LineActor(
        Offset(x = startPoint.x, y = startPoint.y + radius),
        Offset(x = startPoint.x, y = startPoint.y - radius)
    )

    override fun drawInto(drawScope: DrawScope) {
        drawStartLine.drawInto(drawScope)
    }
}