package screen.composables

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import extensions.toFixedDigitsString
import themes.ComposeFonts
import ui.screen.manual.root.CoordinateUiModel

private enum class PositionType(
    val fontSize: TextUnit,
    val width: Dp
) {
    PRIMARY(50.sp, 300.dp),
    SECONDARY(18.sp, 110.dp),
}

private val imageShape = RoundedCornerShape(6.dp)

@Composable
fun AxisCoordinate(
    uiModel: CoordinateUiModel,
    loadedTool: Int? = null,
    isDiameterMode: Boolean = false,
    modifier: Modifier = Modifier,
    zeroPosClicked: () -> Unit,
    absRelClicked: () -> Unit,
    toolOffsetsClicked: () -> Unit
) {
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center
        ) {
            ToolOffsets(
                uiModel,
                modifier = Modifier
                    .clickable(
                        onClick = toolOffsetsClicked
                    )
                    .width(100.dp)
                    .height(100.dp)
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = imageShape,
                    )
            )
            Position(PositionType.SECONDARY, uiModel, isDiameterMode, modifier = Modifier.alignByBaseline())
            AxisLetter(uiModel)
            SpacerOrDiameter(
                uiModel.axis == CoordinateUiModel.Axis.X && isDiameterMode,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Position(PositionType.PRIMARY, uiModel, isDiameterMode, modifier = Modifier.alignByBaseline())
            Units(uiModel.units, modifier = Modifier.alignByBaseline())
            ZeroPos(uiModel, modifier = Modifier.padding(start = 16.dp)) {
                zeroPosClicked.invoke()
            }
            AbsRel(modifier = Modifier.padding(start = 16.dp)) {
                absRelClicked.invoke()
            }
        }
    }
}

@Composable
private fun ToolOffsets(
    uiModel: CoordinateUiModel,
    modifier: Modifier = Modifier
) {
    val image = when (uiModel.axis) {
        CoordinateUiModel.Axis.X -> "x.png"
        CoordinateUiModel.Axis.Z -> "z.png"
    }
    Image(
        modifier = modifier,
        contentDescription = "",
        bitmap = useResource(image) { loadImageBitmap(it) }
    )
}

@Composable
private fun AxisLetter(uiModel: CoordinateUiModel, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.padding(start = 16.dp),
        text = uiModel.axis.name,
        fontSize = 50.sp,
    )
}

@Composable
private fun SpacerOrDiameter(showDiameter: Boolean, modifier: Modifier = Modifier) {
    val sizeToFill = 35.dp
    if (showDiameter) {
        Text(
            modifier = modifier
                .width(sizeToFill)
                .padding(top = 16.dp)
                .fillMaxHeight(),
            text = "\u2300",
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
        )
    } else {
        Spacer(modifier = modifier.width(sizeToFill))
    }
}

@Composable
private fun Position(
    positionType: PositionType,
    uiModel: CoordinateUiModel,
    isDiameterMode: Boolean = false,
    modifier: Modifier = Modifier
) {

    val value = when (positionType) {
        PositionType.PRIMARY -> uiModel.primaryValue
        PositionType.SECONDARY -> uiModel.secondaryValue
    }
    if (value != null) {
        Text(
            modifier = modifier.width(positionType.width),
            text = (value * (if (isDiameterMode) 2 else 1)).toFixedDigitsString(uiModel.displayDigits),
            fontSize = positionType.fontSize,
            fontFamily = ComposeFonts.Family.position,
            fontWeight = FontWeight.Thin,
            textAlign = TextAlign.End
        )
    } else {
        Spacer(modifier = modifier.width(positionType.width))
    }
}

@Composable
private fun Units(units: String, modifier: Modifier = Modifier) {
    Text(
        text = units.lowercase(),
        modifier = modifier,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium
    )
}

@Composable
private fun ZeroPos(uiModel: CoordinateUiModel, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier.fillMaxHeight()
    ) {
        Text("ZERO\n${uiModel.axis.name}-Pos")
    }
}

@Composable
private fun AbsRel(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(onClick = onClick, modifier.fillMaxHeight()) {
        Text("ABS\nREL")
    }
}

