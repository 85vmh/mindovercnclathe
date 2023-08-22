package com.mindovercnc.linuxcnc.screen.programs.programloaded.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindovercnc.linuxcnc.format.toFixedDigitsString
import com.mindovercnc.model.AxisPosition
import com.mindovercnc.model.PositionModel

private enum class CoordinateType(val fontSize: TextUnit, val width: Dp) {
    PRIMARY(28.sp, 140.dp),
    SECONDARY(28.sp, 140.dp),
}

@Composable
fun ProgramCoordinatesView(
    currentWcs: String,
    positionModel: PositionModel,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column {
            CoordinatesHeader(currentWcs)
            Spacer(modifier = Modifier.height(8.dp))
            AxisCoordinate(positionModel.xAxisPos, positionModel.isDiameterMode)
            Spacer(modifier = Modifier.height(8.dp))
            AxisCoordinate(positionModel.zAxisPos)
        }
    }
}

@Composable
private fun CoordinatesHeader(currentWcs: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        Text(modifier = Modifier.padding(end = 124.dp), text = "$currentWcs POS", fontSize = 24.sp)
        Text(modifier = Modifier.padding(end = 32.dp), text = "DTG", fontSize = 24.sp)
    }
}

@Composable
private fun AxisCoordinate(
    axisPosition: AxisPosition,
    isDiameterMode: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Surface(color = MaterialTheme.colorScheme.primaryContainer) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.fillMaxWidth()) {
            // val line = HorizontalAlignmentLine()
            AxisLetter(axisPosition, modifier = Modifier)
            SpacerOrDiameter(
                axisPosition.axis == AxisPosition.Axis.X && isDiameterMode,
                modifier = Modifier
            )
            Position(CoordinateType.PRIMARY, axisPosition, isDiameterMode, modifier = Modifier)
            Units(axisPosition.units, modifier = Modifier)
            Spacer(modifier = Modifier.width(8.dp))
            Position(CoordinateType.SECONDARY, axisPosition, isDiameterMode, modifier = Modifier)
            Units(axisPosition.units, modifier = Modifier)
        }
    }
}

@Composable
private fun AxisLetter(axisPosition: AxisPosition, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.padding(start = 8.dp),
        text = axisPosition.axis.name,
        fontSize = 40.sp,
    )
}

@Composable
private fun SpacerOrDiameter(showDiameter: Boolean, modifier: Modifier = Modifier) {
    val sizeToFill = 30.dp
    if (showDiameter) {
        Text(
            modifier = modifier.width(sizeToFill),
            text = "\u2300",
            textAlign = TextAlign.Start,
            fontSize = 20.sp,
        )
    } else {
        Spacer(modifier = modifier.width(sizeToFill))
    }
}

@Composable
private fun Position(
    positionType: CoordinateType,
    axisPosition: AxisPosition,
    isDiameterMode: Boolean = false,
    modifier: Modifier = Modifier
) {

    val value =
        when (positionType) {
            CoordinateType.PRIMARY -> axisPosition.primaryValue
            CoordinateType.SECONDARY -> axisPosition.secondaryValue
        }
    if (value != null) {
        Text(
            modifier = modifier.width(positionType.width),
            text =
                (value * (if (isDiameterMode) 2 else 1)).toFixedDigitsString(
                    axisPosition.units.displayDigits
                ),
            fontSize = positionType.fontSize,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Thin,
            textAlign = TextAlign.End
        )
    } else {
        Spacer(modifier = modifier.width(positionType.width))
    }
}

@Composable
private fun Units(units: AxisPosition.Units, modifier: Modifier = Modifier) {
    Text(
        text = units.name.lowercase(),
        modifier = modifier.padding(top = 6.dp),
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium
    )
}
