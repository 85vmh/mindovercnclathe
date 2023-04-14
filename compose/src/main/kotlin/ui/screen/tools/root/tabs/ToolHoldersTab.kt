package ui.screen.tools.root.tabs

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mindovercnc.model.LatheTool
import com.mindovercnc.model.ToolHolder
import com.mindovercnc.model.ToolHolderType
import extensions.draggableScroll
import extensions.toFixedDigitsString
import screen.composables.LabelWithValue
import screen.composables.VerticalDivider
import screen.composables.platform.VerticalScrollbar
import ui.screen.tools.root.ToolsScreenModel
import ui.screen.tools.root.tabs.lathetool.DirectionItem
import ui.screen.tools.root.tabs.lathetool.TipOrientation
import ui.screen.tools.root.tabs.toolholder.AddEditHolderScreen


private val itemModifier = Modifier.fillMaxWidth()

private enum class ToolHolderColumn(val text: String, val size: Dp = Dp.Unspecified) {
    Id("ID", 50.dp),
    HolderType("Type", 100.dp),
    Offsets("Tool Offsets", 120.dp),
    ToolInfo("Mounted Tool"),
    Actions("Actions", 210.dp)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToolHoldersContent(
    state: ToolsScreenModel.State,
    onMount: (ToolHolder) -> Unit,
    onDelete: (ToolHolder) -> Unit,
    onLoad: (ToolHolder) -> Unit,
    onHolderChanged: () -> Unit,
    modifier: Modifier = Modifier
) {
    val navigator = LocalNavigator.currentOrThrow
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
    ) {
        val scrollState = rememberLazyListState()

        LazyColumn(
            modifier = Modifier.draggableScroll(scrollState, scope),
            state = scrollState
        ) {
            stickyHeader {
                ToolHolderHeader(modifier = Modifier.height(40.dp))
            }
            itemsIndexed(state.toolHolders) { index, item ->
                ToolHolderView(
                    item = item,
                    isCurrent = item.holderNumber == state.currentTool,
                    onEditClicked = {
                        navigator.push(AddEditHolderScreen(it) {
                            onHolderChanged.invoke()
                        })
                    },
                    onDeleteClicked = onDelete,
                    onLoadClicked = onLoad,
                    onMountClicked = onMount,
                    modifier = itemModifier,
//                    color = gridRowColorFor(index)
                )
                Divider(color = Color.LightGray, thickness = 0.5.dp)
            }
        }

        VerticalScrollbar(
            Modifier.align(Alignment.CenterEnd).width(30.dp),
            scrollState,
            state.toolHolders.size,
            60.dp
        )
    }
}

@Composable
fun ToolHolderHeader(
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ToolHolderColumn.values().forEach {
                val textModifier = when (it.size) {
                    Dp.Unspecified -> Modifier.weight(1f)
                    else -> Modifier.width(it.size)
                }
                Text(
                    modifier = textModifier,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleSmall,
                    text = it.text
                )
                if (it != ToolHolderColumn.values().last()) {
                    VerticalDivider(color = Color.LightGray)
                }
            }
        }
    }
}

@Composable
private fun ToolHolderView(
    item: ToolHolder,
    isCurrent: Boolean,
    onMountClicked: (ToolHolder) -> Unit,
    onEditClicked: (ToolHolder) -> Unit,
    onDeleteClicked: (ToolHolder) -> Unit,
    onLoadClicked: (ToolHolder) -> Unit,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified
) {
    val nonSelectedModifier = Modifier.height(80.dp)
    val selectedModifier = nonSelectedModifier.border(BorderStroke(1.dp, Color.Blue))

    Surface(
        modifier = modifier,
        color = color
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = if (isCurrent) selectedModifier else nonSelectedModifier
        ) {
            Text(
                modifier = Modifier.width(ToolHolderColumn.Id.size),
                text = item.holderNumber.toString(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelLarge
            )
            VerticalDivider()
            Text(
                modifier = Modifier.width(ToolHolderColumn.HolderType.size),
                textAlign = TextAlign.Center,
                text = item.type.name
            )
            VerticalDivider()
            Column(
                modifier = Modifier.width(ToolHolderColumn.Offsets.size),
            ) {
                LabelWithValue("X:", item.xOffset?.toFixedDigitsString() ?: "Not set")
                LabelWithValue("Z:", item.zOffset?.toFixedDigitsString() ?: "Not set")
            }
            VerticalDivider()
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                item.latheTool?.let {
                    LatheToolView(latheTool = it)
                } ?: run {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                onMountClicked.invoke(item)
                            }
                        ) {
                            Text("Mount a Tool")
                        }
                    }
                }
            }
            VerticalDivider()
            Row(
                modifier = Modifier.width(ToolHolderColumn.Actions.size),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(
                    modifier = Modifier,
                    onClick = {
                        onEditClicked.invoke(item)
                    }
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "")
                }
                VerticalDivider()
                IconButton(
                    modifier = Modifier,
                    enabled = isCurrent.not(),
                    onClick = {
                        onDeleteClicked.invoke(item)
                    }
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "")
                }
                VerticalDivider()
                IconButton(
                    modifier = Modifier,
                    enabled = isCurrent.not(),
                    onClick = {
                        onLoadClicked.invoke(item)
                    }
                ) {
                    Icon(Icons.Default.ExitToApp, contentDescription = "")
                }
            }
        }
    }
}

//@Composable
//fun ToolHolderIcon(
//    modifier: Modifier = Modifier,
//    type: ToolHolderType,
//) {
//    val imageResource = when (type) {
//        ToolHolderType.Generic -> "multifix_generic"
//        ToolHolderType.Boring -> "multifix_bore"
//        ToolHolderType.DrillHolder -> "multifix_drill"
//        ToolHolderType.Parting -> "multifix_part"
//    }
//    Box(modifier) {
//        Image(
//            modifier = Modifier.size(80.dp),
//            painter = painterResource(imageResource),
//            contentDescription = ""
//        )
//    }
//}

@Composable
@Preview
fun HolderViewPreview() {
    ToolHolderView(
        ToolHolder(holderNumber = 1, type = ToolHolderType.DrillHolder),
        true,
        {},
        {},
        {},
        {}
    )
}