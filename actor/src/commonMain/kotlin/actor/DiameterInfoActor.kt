package actor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope

class DiameterInfoActor(
    startPoint: Offset,
    radius: Float
) : CanvasActor {

    private val topOffset = Offset(x = startPoint.x, y = startPoint.y - radius)
    private val bottomOffset = Offset(x = startPoint.x, y = startPoint.y + radius)
    private val textOffset = Offset(x = startPoint.x - 10, y = startPoint.y)
    private val actors = mutableListOf<CanvasActor>()

    init {
        actors.add(ArrowTipActor(tipOffset = topOffset))
        actors.add(LineActor(topOffset, bottomOffset))

        val strDiameter = "\u2300".plus(" ").plus(radius * 2)

        actors.add(TextActor(strDiameter, textOffset).rotateBy(270f, textOffset))
        actors.add(ArrowTipActor(tipOffset = bottomOffset).rotateBy(180f, pivot = bottomOffset))
    }

    override fun drawInto(drawScope: DrawScope) {
        actors.forEach { it.drawInto(drawScope) }
    }
}