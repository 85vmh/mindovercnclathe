package ui.screen.manual.root

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import screen.composables.SettingStatusRow

@Composable
fun SpindleStatusView(uiModel: SpindleUiModel, modifier: Modifier = Modifier) {

    data class SpindleModeAndUnits(val mode: String, val value: String, val units: String)

    val spModeWithUnits =
        when (uiModel.isRpmMode) {
            true -> SpindleModeAndUnits("RPM", uiModel.setRpm.toString(), "rev/min")
            else -> SpindleModeAndUnits("CSS", uiModel.setCss.toString(), "m/min")
        }

    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier,
        border = BorderStroke(1.dp, SolidColor(Color.DarkGray)),
        shadowElevation = 4.dp,
    ) {
        val settingsModifier = Modifier.fillMaxWidth()

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.padding(start = 150.dp),
                text = "S",
                fontSize = 45.sp,
            )
            Column(modifier = Modifier.width(300.dp)) {
//                SettingStatusRow(
//                    "Override:",
//                    uiModel.spindleOverride.toString(),
//                    "%",
//                    modifier = settingsModifier
//                )
                SettingStatusRow(
                    "Set:",
                    spModeWithUnits.value,
                    spModeWithUnits.units,
                    modifier = settingsModifier
                )
                if (uiModel.isRpmMode.not()) {
                    SettingStatusRow(
                        "Max RPM:",
                        uiModel.maxRpm.toString(),
                        "rev/min",
                        modifier = settingsModifier
                    )
                }
                SettingStatusRow(
                    "Actual:",
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
}
