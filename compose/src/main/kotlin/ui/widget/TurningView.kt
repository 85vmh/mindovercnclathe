package ui.widget

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.translate
import canvas.*
import usecase.model.VisualTurningState


@Preview
@Composable
fun VisualTurningPreview() {
    VisualTurning(
        VisualTurningState(

        )
    )
}

@Composable
fun VisualTurning(
    state: VisualTurningState,
    modifier: Modifier = Modifier
) {

    val insertShape = InsertShape.Rhomb(
        angle = 55,
        height = state.pixelPerUnit * 7f
    )

//    val insertShape = InsertShape.Drill(
//        diameter = 2.5f * state.pixelPerUnit,
//    )

    Canvas(modifier.fillMaxSize()) {
        //println("--Pixel per unit: ${state.pixelPerUnit}")

        clipRect {
            translate(left = state.translate.x, top = state.translate.y) {
                state.pathUiState.drawInto(this)
                state.referenceActor.drawInto(this)
            }

            translate(left = state.translate.x, top = 0f) {
                state.programRulers.top.drawInto(this)
                state.programRulers.bottom.drawInto(this)
            }

            translate(left = 0f, top = state.translate.y) {
                state.centerLineActor.drawInto(this)
                state.programRulers.left.drawInto(this)
                state.programRulers.right.drawInto(this)
            }

            ToolActor(insertShape = insertShape)
                .translateTo(state.translate)
                .rotateBy(30f, state.translate)
                .translateTo(state.toolPosition.minus(state.wcsPosition).toOffset(state.pixelPerUnit))
                .drawInto(this)
        }
    }
}