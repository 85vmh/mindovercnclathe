package screen.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.widgets.DropDownClosedItem
import com.mindovercnc.linuxcnc.widgets.DropDownView
import com.mindovercnc.model.LatheTool

@Composable
fun DropDownTools(
    settingName: String,
    items: List<LatheTool>,
    selected: LatheTool?,
    dropDownWidth: Dp,
    modifier: Modifier = Modifier,
    onValueChanged: (LatheTool) -> Unit
) {
    val alignment = Alignment.CenterVertically
    Row(verticalAlignment = alignment, modifier = modifier) {
        Text(text = settingName, modifier = Modifier.weight(1f))
        DropDownView(
            items = items,
            selected = selected,
            modifier =
                Modifier.width(dropDownWidth)
                    .border(
                        border = BorderStroke(1.dp, Color.LightGray),
                        shape = RoundedCornerShape(4.dp)
                    ),
            onSelected = onValueChanged,
            closedItemContent = {
                DropDownClosedItem(modifier = Modifier.height(40.dp)) {
                    val text =
                        when {
                            it != null -> "Tool #${it.toolId}"
                            else -> "Select Tool"
                        }
                    Text(text)
                }
            },
            openedItemContent = {
                Text(
                    text = "Tool #${it.toolId}",
                    modifier = Modifier.width(dropDownWidth).padding(8.dp)
                )
            }
        )
    }
}
