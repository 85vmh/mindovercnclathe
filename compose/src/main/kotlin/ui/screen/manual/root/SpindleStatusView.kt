package ui.screen.manual.root

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import extensions.toPercent
import screen.composables.SettingStatusRow
import screen.composables.cards.CardWithTitle

private val settingsModifier = Modifier.fillMaxWidth().padding(8.dp)

@Composable
fun SpindleStatusView(uiModel: SpindleUiModel, onClick: () -> Unit, modifier: Modifier = Modifier) {

  data class SpindleModeAndUnits(val mode: String, val value: String, val units: String)

  val spModeWithUnits =
    when (uiModel.isRpmMode) {
      true -> SpindleModeAndUnits("RPM", uiModel.setRpm.toString(), "rev/min")
      else -> SpindleModeAndUnits("CSS", uiModel.setCss.toString(), "m/min")
    }

  CardWithTitle(
    cardTitle = "Spindle (${uiModel.spindleOverride.toPercent()}%)",
    onClick = onClick,
    modifier = modifier,
    color = MaterialTheme.colorScheme.tertiaryContainer
  ) {
    Column {
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
