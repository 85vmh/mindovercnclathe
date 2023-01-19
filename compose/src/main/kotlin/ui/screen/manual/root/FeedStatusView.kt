package ui.screen.manual.root

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import extensions.toFixedDigitsString
import extensions.toPercent
import screen.composables.SettingStatusRow
import screen.composables.cards.CardWithTitle

private val settingsModifier = Modifier.fillMaxWidth().padding(8.dp)

data class FeedModeAndUnits(val mode: String, val units: String)

@Composable
fun FeedStatusView(uiModel: FeedUiModel, onClick: () -> Unit, modifier: Modifier = Modifier) {
  val feed =
    if (uiModel.isUnitsPerRevMode) {
      FeedModeAndUnits(mode = "Units per revolution", units = "${uiModel.units}/rev")
    } else {
      FeedModeAndUnits(mode = "Units per minute", units = "${uiModel.units}/min")
    }

  CardWithTitle(
    cardTitle = "Feed (${uiModel.feedOverride.toPercent()}%)",
    onClick = onClick,
    color = MaterialTheme.colorScheme.tertiaryContainer,
    modifier = modifier
  ) {
    Column {
      SettingStatusRow("Mode:", feed.mode, modifier = settingsModifier)
      SettingStatusRow(
        "Set feed:",
        uiModel.setFeed.toFixedDigitsString(),
        feed.units,
        modifier = settingsModifier
      )
      SettingStatusRow(
        "Actual feed:",
        uiModel.actualFeed.toFixedDigitsString(),
        feed.units,
        modifier = settingsModifier
      )
    }
  }
}
