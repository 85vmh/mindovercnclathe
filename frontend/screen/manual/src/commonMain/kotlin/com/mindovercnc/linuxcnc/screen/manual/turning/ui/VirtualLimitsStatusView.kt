package com.mindovercnc.linuxcnc.screen.manual.turning.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.format.toFixedDigitsString
import com.mindovercnc.model.VirtualLimitsUiModel
import com.mindovercnc.linuxcnc.widgets.cards.CardWithTitle

@Composable
fun VirtualLimitsStatusView(
    virtualLimits: VirtualLimitsUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CardWithTitle(
        modifier = modifier,
        onClick = onClick,
        cardTitle = "Virtual Limits",
    ) {
        Column {
            virtualLimits.xMinus?.let { AxisLimit("X-", it) }
            virtualLimits.xPlus?.let { AxisLimit("X+", it) }
            virtualLimits.zMinus?.let { AxisLimit("Z-", it) }
            virtualLimits.zPlus?.let { AxisLimit("Z+", it) }
        }
    }
}

@Composable
private fun AxisLimit(axisDirection: String, value: Double) {
    Text("$axisDirection : ${value.toFixedDigitsString()}")
}
