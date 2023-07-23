package actor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope

class ChamferActor(
    val startPoint: Offset,
    val width: Float,
    val startHeight: Float,
    val endHeight: Float
) : CanvasActor {

    val topLine = LineActor(
        Offset(x = startPoint.x, y = startPoint.y - startHeight),
        Offset(x = startPoint.x + width, y = startPoint.y - endHeight))

    val bottomLine = LineActor(
        Offset(x = startPoint.x, y = startPoint.y + startHeight),
        Offset(x = startPoint.x + width, y = startPoint.y + endHeight))

    override fun drawInto(drawScope: DrawScope) {
        topLine.drawInto(drawScope)
        bottomLine.drawInto(drawScope)
    }
}