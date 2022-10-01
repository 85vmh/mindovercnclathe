package canvas

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate

class ZoomActor(
    private vararg val actors: CanvasActor,
    private val scale: Float,
    private val pivot: Offset? = null
) : CanvasActor {

    override fun drawInto(drawScope: DrawScope) {
        drawScope.scale(scale, pivot ?: drawScope.drawContext.size.center) {
            actors.forEach { it.drawInto(this) }
        }
    }
}