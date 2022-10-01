package ui.screen.manual.root

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
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
import screen.composables.SettingStatusRow

@Composable
fun SpindleStatusView(
    uiModel: SpindleUiModel,
    modifier: Modifier = Modifier
) {

    data class SpindleModeAndUnits(val mode: String, val value: String, val units: String)

    val spModeWithUnits = when (uiModel.isRpmMode) {
        true -> SpindleModeAndUnits("RPM", uiModel.setRpm.toString(), "rev/min")
        else -> SpindleModeAndUnits("CSS", uiModel.setCss.toString(), "m/min")
    }

    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier,
        border = BorderStroke(1.dp, SolidColor(Color.DarkGray)),
        shadowElevation = 8.dp,
    ) {
        val settingsModifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)

        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = "Spindle"
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    text = "(${uiModel.spindleOverride}%)"
                )
            }
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = Color.DarkGray,
                thickness = 1.dp
            )
            /*
            * RPM
            * - Set RPM: 1500 rev/min
            * - Actual RPM
            * - Stop at:
            *
            * CSS
            * - Set CSS: 200 m/min
            * - Max RPM: 2000 rev/min
            * - Actual RPM
            * - Stop at:
            * */

            SettingStatusRow(
                "Set ${spModeWithUnits.mode}:",
                spModeWithUnits.value,
                spModeWithUnits.units,
                modifier = settingsModifier
            )
            if (uiModel.isRpmMode.not()) {
                SettingStatusRow("Max RPM:", uiModel.maxRpm.toString(), "rev/min", modifier = settingsModifier)
            }
            SettingStatusRow(
                "Actual RPM:",
                kotlin.math.abs(uiModel.actualRpm).toString(),
                "rev/min",
                modifier = settingsModifier
            )
            uiModel.stopAngle?.let {
                SettingStatusRow("Oriented stop:", it.toString(), "degrees", modifier = settingsModifier)
            }
        }
    }
}