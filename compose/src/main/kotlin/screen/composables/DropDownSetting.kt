package screen.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DropDownSetting(
    settingName: String,
    items: List<T>,
    selectedItem: T?,
    dropDownWidth: Dp,
    nothingSelectedString: String = "Select Item",
    modifier: Modifier = Modifier,
    onValueChanged: (T) -> Unit
) {
    ListItem(
        modifier = modifier,
        headlineText = { Text(text = settingName) },
        trailingContent = {
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
    )
}
