package canvas

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import usecase.model.ProgramData
import usecase.model.VisualTurningState

class ToolTraceActor(
    private val programData: ProgramData,
    private val tracePathColor: Color = Color.DarkGray,
    private val traceThickness: Float = 1f,
) : CanvasActor {

    override fun drawInto(drawScope: DrawScope) {
        drawScope.drawPath(
            path = programData.tracePath,
            color = tracePathColor,
            style = Stroke(width = traceThickness, cap = StrokeCap.Round)
        )
    }
}