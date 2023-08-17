package actor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope

class CenterLineActor : CanvasActor {
    companion object {
        const val thickness: Float = 0.3f
        const val dash: Float = 15f
        const val dot: Float = 5f
        const val space: Float = 15f
    }

    override fun drawInto(drawScope: DrawScope) {
        LineActor(
            start = Offset(0f, 0f),
            end = Offset(drawScope.size.width, 0f),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(dash, space, dot, space), 1f),
            thickness = thickness
        ).drawInto(drawScope)
    }
}