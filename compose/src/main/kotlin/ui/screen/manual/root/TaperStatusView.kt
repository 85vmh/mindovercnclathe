package ui.screen.manual.root

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import extensions.stripZeros
import screen.composables.SettingStatusRow
import screen.composables.cards.ExpandableCardWithTitle

private val settingsModifier = Modifier.fillMaxWidth().padding(8.dp)

@Composable
fun TaperStatusView(
  taperAngle: Double,
  expanded: Boolean,
  onExpandChange: (Boolean) -> Unit,
  onClick: () -> Unit,
  enabled: Boolean = true,
  modifier: Modifier = Modifier
) {
  ExpandableCardWithTitle(
    cardTitle = "Taper Turning",
    expanded = expanded,
    onExpandChange = onExpandChange,
    modifier = modifier,
    onClick = onClick,
    enabled = enabled,
    color = MaterialTheme.colorScheme.tertiaryContainer
  ) {
    SettingStatusRow(
      settingText = "Taper angle:",
      settingValue = taperAngle.stripZeros(),
      settingUnit = "degrees",
      modifier = settingsModifier
    )
  }
}
