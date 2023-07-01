package ui.screen.tools.root.tabs.toolholder

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mindovercnc.model.LatheTool
import com.mindovercnc.model.ToolHolderType
import extensions.draggableScroll
import screen.composables.VerticalDivider
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
    val toolsScrollState = rememberLazyListState()
    val holdersScrollState = rememberLazyGridState()

    Row(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.width(300.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.holderNumber == null) {
                ValueSetting(
                    settingName = "Holder #",
                    value = "0",
                    inputType = InputType.TOOL_HOLDER_NO,
                    onValueChanged = {
                        val doubleValue = it.toDouble()
                        onHolderNumber(doubleValue.toInt())
                    },
                    modifier = Modifier.fillMaxWidth(),
                    inputModifier = Modifier.width(100.dp)
                )
                Divider()
                Spacer(Modifier.height(24.dp))
            }

            Text(
                modifier = Modifier.padding(4.dp),
                text = "Tool Holder Type",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall
            )

            LazyVerticalGrid(
                modifier = modifier.draggableScroll(holdersScrollState, scope),
                state = holdersScrollState,
                contentPadding = PaddingValues(8.dp),
                columns = GridCells.Adaptive(120.dp),
            ) {
                items(items.size) { index ->
                    ToolHolderView(
                        modifier = Modifier.padding(8.dp),
                        type = items[index],
                        onClick = onHolderType,
                        isSelected = items[index] == state.type
                    )
                }
            }
        }
        VerticalDivider()
        Column(
            modifier = Modifier.padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier.padding(4.dp),
                text = "Tools not mounted yet",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall
            )

            LazyColumn(modifier = Modifier.draggableScroll(toolsScrollState, scope), state = toolsScrollState) {
                itemsIndexed(state.unmountedLatheTools) { _, item ->
                    LatheToolView(
                        latheTool = item,
                        onSelected = onLatheTool,
                        isSelected = item == state.latheTool
                    )
                    Divider(color = Color.LightGray, thickness = 0.5.dp)
                }
            }
        }
    }
}

@Composable
@Preview
fun AddEditHolderContentPreview() {
    AddEditHolderContent(AddEditToolHolderScreenModel.State(), {}, {}, {})
}
