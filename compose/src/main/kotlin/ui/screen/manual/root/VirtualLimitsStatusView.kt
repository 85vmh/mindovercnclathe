package ui.screen.manual.root

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import extensions.toFixedDigitsString

@Composable
fun VirtualLimitsStatusView(
    virtualLimits: VirtualLimitsUiModel,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier,
        border = BorderStroke(1.dp, SolidColor(Color.DarkGray)),
        shadowElevation = 16.dp
    ) {
        Column {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleMedium,
                text = "Virtual Limits"
            )
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = Color.DarkGray,
                thickness = 1.dp
            )
            virtualLimits.xMinus?.let {
                AxisLimit("X-", it)
            }
            virtualLimits.xPlus?.let {
                AxisLimit("X+", it)
            }
            virtualLimits.zMinus?.let {
                AxisLimit("Z-", it)
            }
            virtualLimits.zPlus?.let {
                AxisLimit("Z+", it)
            }
        }
    }
}

@Composable
fun AxisLimit(axisDirection: String, value: Double) {
    Text("$axisDirection : ${value.toFixedDigitsString()}")
}