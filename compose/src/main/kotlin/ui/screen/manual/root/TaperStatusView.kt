package ui.screen.manual.root

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import extensions.stripZeros
import screen.composables.SettingStatusRow
import ui.widget.ExpandIcon

private val settingsModifier = Modifier
    .fillMaxWidth()
    .padding(8.dp)

@Composable
fun TaperStatusView(
    taperAngle: Double,
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier,
        border = BorderStroke(1.dp, SolidColor(Color.DarkGray)),
        shadowElevation = 16.dp
    ) {
        Column {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.titleMedium,
                    text = "Taper Turning"
                )
                ExpandIcon(
                    expanded = expanded,
                    onExpandChange = onExpandChange,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
            if (expanded) {
                Divider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color.DarkGray,
                    thickness = 1.dp
                )
                SettingStatusRow(
                    settingText = "Taper angle:",
                    settingValue = taperAngle.stripZeros(),
                    settingUnit = "degrees",
                    modifier = settingsModifier
                )
            }
        }
    }
}