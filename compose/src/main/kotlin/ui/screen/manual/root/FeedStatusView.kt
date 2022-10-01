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
import extensions.toFixedDigitsString
import screen.composables.SettingStatusRow

@Composable
fun FeedStatusView(
    uiModel: FeedUiModel,
    modifier: Modifier = Modifier
) {

    data class FeedModeAndUnits(val mode: String, val units: String)

    val feed = when (uiModel.isUnitsPerRevMode) {
        true -> FeedModeAndUnits("Units per revolution", "${uiModel.units}/rev")
        else -> FeedModeAndUnits("Units per minute", "${uiModel.units}/min")
    }

    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier,
        border = BorderStroke(1.dp, SolidColor(Color.DarkGray)),
        shadowElevation = 16.dp
    ) {
        val settingsModifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)

        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            )
            {
                Text(
                    modifier = Modifier,
                    style = MaterialTheme.typography.titleMedium,
                    text = "Feed"
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    text = "(${uiModel.feedOverride.toFixedDigitsString(0)}%)"
                )
            }
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = Color.DarkGray,
                thickness = 1.dp
            )
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