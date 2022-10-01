package canvas

import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate

class TranslateActor(
    private vararg val actors: CanvasActor,
    private val left: Float,
    private val top: Float
) : CanvasActor {

    override fun drawInto(drawScope: DrawScope) {
        drawScope.translate(left, top) {
            actors.forEach { it.drawInto(this) }
        }
    }
}