package screen.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun <T> DropDownSetting(
  settingName: String? = null,
  items: List<T>,
  selectedItem: T?,
  dropDownWidth: Dp,
  nothingSelectedString: String = "Select Item",
  modifier: Modifier = Modifier,
  onValueChanged: (T) -> Unit
) {
  val alignment = Alignment.CenterVertically
  Row(verticalAlignment = alignment, modifier = modifier) {
    settingName?.let { Text(text = it, modifier = Modifier.weight(1f)) }
    DropDownView(
      items = items,
      selected = selectedItem,
      modifier = Modifier.width(dropDownWidth),
      onSelected = onValueChanged,
      closedItemContent = {
        DropDownClosedItem(
          modifier =
            Modifier.size(height = 40.dp, width = dropDownWidth)
              .border(
                border = BorderStroke(1.dp, Color.LightGray),
                shape = RoundedCornerShape(4.dp)
              )
        ) {
          val text =
            when {
              it != null -> it.toString()
              else -> nothingSelectedString
            }
          Text(text = text)
        }
      },
      openedItemContent = {
        Text(
          it.toString(),
          modifier = Modifier.width(dropDownWidth).padding(8.dp),
        )
      }
    )
  }
}
