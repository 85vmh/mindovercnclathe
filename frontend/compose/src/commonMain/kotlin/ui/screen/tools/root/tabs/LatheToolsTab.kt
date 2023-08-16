package ui.screen.tools.root.tabs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mindovercnc.model.CuttingInsert
import com.mindovercnc.model.LatheTool
import com.mindovercnc.linuxcnc.format.toFixedDigitsString
import com.mindovercnc.linuxcnc.widgets.VerticalDivider
import scroll.VerticalScrollbar
import scroll.draggableScroll
import ui.screen.tools.root.ToolsScreenModel
import ui.screen.tools.root.tabs.lathetool.AddEditLatheToolScreen
import ui.screen.tools.root.tabs.lathetool.DirectionItem
import ui.screen.tools.root.tabs.lathetool.TipOrientationUi
import com.mindovercnc.linuxcnc.listitem.LabelWithValue

private val itemModifier = Modifier.fillMaxWidth()

private enum class LatheToolColumns(val text: String, val size: Dp = Dp.Unspecified) {
    Id("ID", 50.dp),
    ToolType("Type", 100.dp),
    Details("Tool Details"),
    Orientation("Orient", 60.dp),
    Rotation("Spindle", 60.dp),
    Usage("Usage", 100.dp),
    Actions("Actions", 140.dp),
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LatheToolsContent(
    state: ToolsScreenModel.State,
    modifier: Modifier = Modifier,
    onDelete: (LatheTool) -> Unit,
    onToolChanged: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val navigator = LocalNavigator.currentOrThrow

    Box(modifier = modifier) {
        val scrollState = rememberLazyListState()

        LazyColumn(modifier = Modifier.draggableScroll(scrollState, scope), state = scrollState) {
            stickyHeader { LatheToolHeader(modifier = Modifier.height(40.dp)) }
            itemsIndexed(state.latheTools) { index, item ->
                Surface(
                    // color = gridRowColorFor(index)
                ) {
                    GenericToolView(
                        index = index,
                        item = item,
                        onEditClicked = {
                            navigator.push(AddEditLatheToolScreen(it) { onToolChanged.invoke() })
                        },
                        onDeleteClicked = onDelete,
                        modifier = itemModifier
                    ) {
                        when (item) {
                            is LatheTool.Turning -> TurningToolView(item = item, modifier = itemModifier)
                            is LatheTool.Boring -> BoringToolView(item = item, modifier = itemModifier)
                            is LatheTool.Drilling -> DrillingToolView(item = item, modifier = itemModifier)
                            is LatheTool.Parting -> PartingToolView(item = item, modifier = itemModifier)
                            else -> {
                                Text("Not implemented: $item")
                            }
                        }
                    }
                }
                HorizontalDivider()
            }
        }

        VerticalScrollbar(
            Modifier.align(Alignment.CenterEnd).width(30.dp),
            scrollState
        )
    }
}

@Composable
fun LatheToolHeader(modifier: Modifier = Modifier) {
    Surface(color = MaterialTheme.colorScheme.primaryContainer) {
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            LatheToolColumns.entries.forEach {
                val textModifier =
                    when (it.size) {
                        Dp.Unspecified -> Modifier.weight(1f)
                        else -> Modifier.width(it.size)
                    }
                Text(
                    modifier = textModifier,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleSmall,
                    text = it.text
                )
                if (it != LatheToolColumns.entries.last()) {
                    VerticalDivider(color = Color.LightGray)
                }
            }
        }
    }
}

@Composable
private fun GenericToolView(
    index: Int,
    item: LatheTool,
    onEditClicked: (LatheTool) -> Unit,
    onDeleteClicked: (LatheTool) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.height(60.dp)
    ) {
        Text(
            text = (index + 1).toString(),
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(LatheToolColumns.Id.size),
        )
        VerticalDivider()
        Text(
            text = item::class.simpleName!!,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.width(LatheToolColumns.ToolType.size)
        )
        VerticalDivider()
        Column(modifier = Modifier.padding(horizontal = 8.dp).weight(1f)) { content.invoke() }
        VerticalDivider()
        Row(
            modifier = Modifier.width(LatheToolColumns.Orientation.size),
            horizontalArrangement = Arrangement.Center
        ) {
            TipOrientationUi(
                orientation = item.tipOrientation,
                modifier = Modifier.padding(8.dp),
                onClick = {}
            )
        }
        VerticalDivider()
        Row(
            modifier = Modifier.width(LatheToolColumns.Rotation.size).padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            DirectionItem(spindleDirection = item.spindleDirection)
        }
        VerticalDivider()
        UsageTime(seconds = item.secondsUsed, modifier = Modifier.width(LatheToolColumns.Usage.size))
        VerticalDivider()
        Row(
            modifier = Modifier.width(LatheToolColumns.Actions.size),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(modifier = Modifier, onClick = { onEditClicked.invoke(item) }) {
                Icon(Icons.Default.Edit, contentDescription = "")
            }
            VerticalDivider()
            IconButton(modifier = Modifier, onClick = { onDeleteClicked.invoke(item) }) {
                Icon(Icons.Default.Delete, contentDescription = "")
            }
        }
    }
}

@Composable
private fun TurningToolView(
    item: LatheTool.Turning,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.height(60.dp)
    ) {
        CutterProperties(insert = item.insert, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun BoringToolView(
    item: LatheTool.Boring,
    modifier: Modifier = Modifier,
) {
    CutterProperties(insert = item.insert, modifier = modifier)
    LabelWithValue(
        label = "Minimum Diameter:",
        value = item.minBoreDiameter.toFixedDigitsString(1),
        paddingStart = 0.dp
    )
}

@Composable
private fun DrillingToolView(
    item: LatheTool.Drilling,
    modifier: Modifier = Modifier,
) {
    LabelWithValue(
        label = "Tool Diameter:",
        value = item.toolDiameter.toFixedDigitsString(1),
        paddingStart = 0.dp
    )
    LabelWithValue(
        label = "Maximum Z Depth:",
        value = item.maxZDepth.toFixedDigitsString(1),
        paddingStart = 0.dp
    )
}

@Composable
private fun PartingToolView(
    item: LatheTool.Parting,
    modifier: Modifier = Modifier,
) {
    CutterProperties(insert = item.insert, modifier = modifier)
    LabelWithValue(
        label = "Blade Width:",
        value = item.bladeWidth.toFixedDigitsString(1),
        paddingStart = 0.dp
    )
    LabelWithValue(
        label = "Maximum X Depth:",
        value = item.maxXDepth.toFixedDigitsString(1),
        paddingStart = 0.dp
    )
}

@Composable
fun CutterProperties(insert: CuttingInsert, modifier: Modifier = Modifier) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = "Cutter Type:", style = MaterialTheme.typography.labelLarge)
        Text(text = insert.madeOf.name, style = MaterialTheme.typography.bodyMedium)

        Text(
            text = insert.code ?: "--",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "r${insert.tipRadius.toFixedDigitsString(1)}",
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun HorizontalDivider() {
    Divider(color = Color.LightGray, thickness = 0.5.dp)
}

@Composable
private fun UsageTime(seconds: Double, modifier: Modifier = Modifier.wrapContentWidth()) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.DateRange, contentDescription = "")
        Text(text = "00:05:48", style = MaterialTheme.typography.bodyMedium)
    }
}
