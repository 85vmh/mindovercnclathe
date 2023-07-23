package actor

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke

private const val dash = 2f
private const val space = 5f

data class PathActor(
    private val pathElements: List<PathElement> = emptyList(),
    private val pixelPerUnit: Float = 1f,
    private val feedPathColor: Color = Color.Black,
    private val traversePathColor: Color = Color(red = 0, green = 100, blue = 0),
    private val feedThickness: Float = 1f,
    private val traverseThickness: Float = 0.3f,
) : CanvasActor {
    private val traverseEffect = PathEffect.dashPathEffect(floatArrayOf(dash, space), 1f)

    val programData: ProgramData = pathElements.toProgramData(pixelPerUnit)
    val programSize: ProgramData.ProgramSize get() = programData.programSize

    val xPlusExtents: Float
        get() = programData.xPlusExtents

    val zPlusExtents: Float
        get() = programData.zPlusExtents

    override fun drawInto(drawScope: DrawScope) {
        drawScope.drawPath(
            path = programData.feedPath,
            color = feedPathColor,
            style = Stroke(width = feedThickness, cap = StrokeCap.Round)
        )
        drawScope.drawPath(
            path = programData.traversePath,
            color = traversePathColor,
            style = Stroke(width = traverseThickness, cap = StrokeCap.Round)
        )
    }

    fun rescaled(pixelPerUnit: Float) = copy(pixelPerUnit = pixelPerUnit)

    private fun List<PathElement>.toProgramData(pixelPerUnit: Float): ProgramData {
        val fp = Path()
        val tp = Path()
        forEach {
            when (it) {
                is PathElement.Line -> {
                    when (it.type) {
                        PathElement.Line.Type.Feed -> fp.addLine(it, pixelPerUnit)
                        PathElement.Line.Type.Traverse -> tp.addLine(it, pixelPerUnit)
                    }
                }
                is PathElement.Arc -> fp.addArc(it, pixelPerUnit)
            }
        }
        fp.close()
        tp.close()

        return ProgramData(
            feedPath = fp,
            traversePath = tp
        )
    }

    private fun List<Point2D>.toPath(pixelPerUnit: Float): Path {
        val path = Path()
        val previousPoint = firstOrNull()
        if (previousPoint != null) {
            with(previousPoint.toOffset(pixelPerUnit)) {
                path.moveTo(x, y)
            }
            this.forEach {
                with(it.toOffset(pixelPerUnit)) {
                    path.lineTo(x, y)
                }
            }
        }
        return path
    }
}