package actor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope

class CylinderActor(
    val startPoint: Offset,
    val radius: Float,
    val length: Float
) : ShapeActor {

    private val horizontalLineTop = LineActor(
        start = Offset(startPoint.x, startPoint.y + radius),
        end = Offset(startPoint.x + length, startPoint.y + radius),
    )

    private val horizontalLineBottom = LineActor(
        start = Offset(startPoint.x, startPoint.y - radius),
        end = Offset(startPoint.x + length, startPoint.y - radius),
    )

    val diameterInfo = DiameterInfoActor(
        startPoint = Offset(x = startPoint.x + length / 2, y = startPoint.y),
        radius = radius
    )

    override fun drawInto(drawScope: DrawScope) {
        horizontalLineTop.drawInto(drawScope)
        horizontalLineBottom.drawInto(drawScope)
        diameterInfo.drawInto(drawScope)
    }
}