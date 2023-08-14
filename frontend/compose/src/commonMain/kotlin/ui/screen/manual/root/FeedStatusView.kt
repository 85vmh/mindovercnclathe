package ui.screen.manual.root

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindovercnc.linuxcnc.format.toFixedDigitsString
import screen.composables.SettingStatusRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedStatusView(
  uiModel: FeedUiModel,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {

  data class FeedModeAndUnits(val mode: String, val units: String)

  val feed = when (uiModel.isUnitsPerRevMode) {
    true -> FeedModeAndUnits("Units per revolution", "${uiModel.units}/rev")
    else -> FeedModeAndUnits("Units per minute", "${uiModel.units}/min")
  }

  Surface(
    modifier = modifier,
    shape = RoundedCornerShape(8.dp),
    border = BorderStroke(1.dp, SolidColor(Color.DarkGray)),
    color = MaterialTheme.colorScheme.tertiaryContainer,
    shadowElevation = 8.dp,
    onClick = onClick
  ) {
    val settingsModifier = Modifier.fillMaxWidth()

    Column {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      )
      {
        Text(
          modifier = Modifier.padding(start = 150.dp),
          text = "F",
          fontSize = 45.sp,
        )
        Column(modifier = Modifier.width(300.dp)) {
//                    SettingStatusRow(
//                        modifier = settingsModifier,
//                        settingText = "Override:",
//                        settingValue = uiModel.feedOverride.toFixedDigitsString(0),
//                        settingUnit = "%"
//                    )
          SettingStatusRow(
            settingText = "Set:",
            settingValue = uiModel.setFeed.toFixedDigitsString(),
            settingUnit = feed.units,
            modifier = settingsModifier
          )
          SettingStatusRow(
            settingText = "Actual:",
            settingValue = uiModel.actualFeed.toFixedDigitsString(),
            settingUnit = feed.units,
            modifier = settingsModifier
          )
        }
      }
    }
  }
}