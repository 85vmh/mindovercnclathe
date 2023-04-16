package ui.screen.tools.root.tabs.toolholder

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mindovercnc.model.LatheTool
import com.mindovercnc.model.ToolHolderType
import extensions.draggableScroll
import screen.uimodel.InputType
import ui.screen.tools.root.tabs.LatheToolView
import ui.widget.ValueSetting

@Composable
fun AddEditHolderContent(
  state: AddEditToolHolderScreenModel.State,
  onHolderNumber: (Int) -> Unit,
  onHolderType: (ToolHolderType) -> Unit,
  onLatheTool: (LatheTool) -> Unit,
  modifier: Modifier = Modifier
) {
  val scope = rememberCoroutineScope()
  val items = remember { ToolHolderType.values() }

  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(24.dp),
      contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
      items(items) { toolHolderType ->
        ToolHolderView(
          type = toolHolderType,
          onClick = onHolderType,
          isSelected = toolHolderType == state.type
        )
      }
    }

    if (state.holderNumber == null) {
      ValueSetting(
        settingName = "Holder #",
        value = "0",
        inputType = InputType.TOOL_HOLDER_NO,
        onValueChanged = {
          val doubleValue = it.toDouble()
          onHolderNumber(doubleValue.toInt())
        },
        modifier = Modifier.fillMaxWidth()
      )
    }

    val scrollState = rememberLazyListState()

    LazyColumn(modifier = Modifier.draggableScroll(scrollState, scope), state = scrollState) {
      itemsIndexed(state.latheToolsList) { index, item ->
        LatheToolView(
          latheTool = item,
          onSelected = { onLatheTool.invoke(it) },
          isSelected = item == state.latheTool
        )
        Divider(color = Color.LightGray, thickness = 0.5.dp)
      }
    }
  }
}

@Composable
@Preview
fun AddEditHolderContentPreview() {
  AddEditHolderContent(AddEditToolHolderScreenModel.State(), {}, {}, {})
}
