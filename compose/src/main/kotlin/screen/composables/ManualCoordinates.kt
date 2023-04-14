package screen.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
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

@Composable
fun AxisCoordinate(
    uiModel: CoordinateUiModel,
    setHeight: Dp,
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
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Position(PositionType.SECONDARY, uiModel, isDiameterMode, modifier = Modifier.alignByBaseline())
            AxisLetter(
                uiModel = uiModel,
                isDiameterMode = isDiameterMode,
                modifier = Modifier
                    .clickable(
                        onClick = toolOffsetsClicked
                    )
            )
            Position(PositionType.PRIMARY, uiModel, isDiameterMode, modifier = Modifier.alignByBaseline())
            Units(uiModel.units, modifier = Modifier.alignByBaseline())
            ZeroPos(modifier = Modifier) {
                zeroPosClicked.invoke()
            }
            AbsRel(
                modifier = Modifier.padding(start = 16.dp),
                isIncremental = uiModel.isIncremental,
                onClick = absRelClicked
            )
        }
    }
}

@Composable
private fun AxisLetter(
    uiModel: CoordinateUiModel,
    isDiameterMode: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(4.dp),
        modifier = modifier.size(60.dp),
        border = BorderStroke(1.dp, SolidColor(Color.DarkGray)),
        shadowElevation = 16.dp
    ) {
        Text(
            modifier = modifier.fillMaxSize(),
            text = uiModel.axis.name,
            fontSize = 45.sp,
            textAlign = TextAlign.Center
        )
        if (uiModel.axis == CoordinateUiModel.Axis.X && isDiameterMode) {
            Text(
                modifier = modifier
                    .width(35.dp)
                    .padding(top = 18.dp, end = 6.dp)
                    .fillMaxHeight(),
                text = "\u2300",
                textAlign = TextAlign.Right,
                fontSize = 20.sp,
            )
        }
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
private fun ZeroPos(modifier: Modifier = Modifier, onClick: () -> Unit) {
    val innerPadding = 10.dp
    val distanceBetweenLines = 16.dp
    val linesColor = Color.DarkGray
    val linesCap = StrokeCap.Round
    val lineThickness = 1.5.dp

    Surface(
        shape = RoundedCornerShape(4.dp),
        modifier = modifier
            .size(60.dp)
            .clickable { onClick.invoke() },
        border = BorderStroke(1.dp, SolidColor(Color.DarkGray)),
        shadowElevation = 16.dp
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawLine(
                start = Offset(0f + innerPadding.toPx(), this.size.height - innerPadding.toPx() - distanceBetweenLines.toPx() / 2),
                end = Offset(this.size.width - innerPadding.toPx() - distanceBetweenLines.toPx() / 2, 0f + innerPadding.toPx()),
                color = linesColor,
                cap = linesCap,
                strokeWidth = lineThickness.toPx()
            )
            drawLine(
                start = Offset(0f + innerPadding.toPx() + distanceBetweenLines.toPx() / 2, this.size.height - innerPadding.toPx()),
                end = Offset(this.size.width - innerPadding.toPx(), 0f + innerPadding.toPx() + distanceBetweenLines.toPx() / 2),
                color = linesColor,
                cap = linesCap,
                strokeWidth = lineThickness.toPx()
            )
        }
    }
}

@Composable
private fun AbsRel(
    modifier: Modifier = Modifier,
    isIncremental: Boolean,
    onClick: () -> Unit
) {
    val absColor = if (!isIncremental) Color.DarkGray else Color.DarkGray.copy(alpha = 0.2f)
    val incColor = if (isIncremental) Color.DarkGray else Color.DarkGray.copy(alpha = 0.2f)

    Surface(
        shape = RoundedCornerShape(4.dp),
        modifier = modifier
            .size(60.dp)
            .clickable { onClick.invoke() },
        border = BorderStroke(1.dp, SolidColor(Color.DarkGray)),
        shadowElevation = 16.dp
    ) {
        val textPadding: Dp = 4.dp
        Box(modifier = Modifier
            .drawBehind {
                drawLine(
                    start = Offset(0f, this.size.height),
                    end = Offset(this.size.width, 0f),
                    color = Color.LightGray
                )
            }) {
            Text(
                text = "ABS",
                color = absColor,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = textPadding, top = textPadding)
            )
            Text(
                text = "INC",
                color = incColor,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = textPadding, bottom = textPadding)
            )
        }
    }
}

