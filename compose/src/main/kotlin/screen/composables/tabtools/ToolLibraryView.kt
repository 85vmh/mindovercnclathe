package screen.composables.tabtools

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mindovercnc.model.LinuxCncTool
import extensions.draggableScroll
import extensions.toFixedDigitsString
import org.kodein.di.compose.rememberInstance
import screen.composables.LabelWithValue
import screen.composables.VerticalDivider
import screen.composables.platform.VerticalScrollbar
import usecase.ToolsUseCase


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun ToolLibraryView(
    modifier: Modifier
) {
    val useCase: ToolsUseCase by rememberInstance()
    val scope = rememberCoroutineScope()
    val toolsList by useCase.getTools().collectAsState(emptyList())
    val currentToolNo by useCase.getCurrentToolNo().collectAsState(null)
    //val currentTool by useCase.getCurrentTool().collectAsState(null)

    var toolToDelete by remember { mutableStateOf(0) }

    if (toolToDelete > 0) {
        AlertDialog(
            onDismissRequest = { toolToDelete = 0 },
            title = {
                Text(text = "Confirmation", fontWeight = FontWeight.Bold)
            },
            text = {
                Row(
                    modifier = Modifier.width(250.dp)
                ) {
                    Text("Delete tool $toolToDelete ?")
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        useCase.deleteTool(toolToDelete)
                        toolToDelete = 0
                    }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        toolToDelete = 0
                    }) {
                    Text("Cancel")
                }
            },
            shape = RoundedCornerShape(32.dp),
            backgroundColor = MaterialTheme.colorScheme.secondary,
        )
    }

    Box(
        modifier = modifier
    ) {
        val scrollState = rememberLazyListState()

        LazyColumn(
            modifier = Modifier.draggableScroll(scrollState, scope),
            state = scrollState
        ) {
//            stickyHeader {
//                ToolsHeader()
//                Divider(color = Color.LightGray, thickness = 0.5.dp)
//            }
//            items(toolsList) { item ->
//                ToolRow(
//                    item = item,
//                    isCurrent = item.toolNo == currentToolNo,
//                    onEditClicked = {
//
//                    },
//                    onDeleteClicked = {
//                        toolToDelete = it.toolNo
//                    },
//                    onLoadClicked = {
//                        //useCase.loadTool(it.toolNo)
//                    }
//                )
//                Divider(color = Color.LightGray, thickness = 0.5.dp)
//            }
        }

        VerticalScrollbar(
            Modifier.align(Alignment.CenterEnd).width(30.dp),
            scrollState,
            toolsList.size,
            60.dp
        )
    }
}

private enum class ToolsColumnModifier(val modifier: Modifier) {
    ToolNo(Modifier.width(50.dp)),
    Offset(Modifier.width(130.dp)),
    Wear(Modifier.width(130.dp)),
    TipRadius(Modifier.width(100.dp)),
    Orientation(Modifier.width(110.dp)),
}

@Composable
fun ToolsHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(Color.White)
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = ToolsColumnModifier.ToolNo.modifier,
            textAlign = TextAlign.Center,
            text = "Tool"
        )
        VerticalDivider()
        Text(
            modifier = ToolsColumnModifier.Offset.modifier,
            textAlign = TextAlign.Center,
            text = "Offset"
        )
        VerticalDivider()
//        Text(
//            modifier = ToolsColumnModifier.Wear.modifier,
//            textAlign = TextAlign.Center,
//            text = "Wear"
//        )
//        VerticalDivider()
        Text(
            modifier = ToolsColumnModifier.TipRadius.modifier,
            textAlign = TextAlign.Center,
            text = "Tip Radius"
        )
        VerticalDivider()
        Text(
            modifier = ToolsColumnModifier.Orientation.modifier,
            textAlign = TextAlign.Center,
            text = "Orientation"
        )
        VerticalDivider()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToolRow(
    item: LinuxCncTool,
    isCurrent: Boolean,
    modifier: Modifier = Modifier,
    onEditClicked: (LinuxCncTool) -> Unit,
    onDeleteClicked: (LinuxCncTool) -> Unit,
    onLoadClicked: (LinuxCncTool) -> Unit
) {
    val nonSelectedModifier = modifier.height(60.dp)
    val selectedModifier = nonSelectedModifier.border(BorderStroke(1.dp, Color.Blue))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = if (isCurrent) selectedModifier else nonSelectedModifier
//            .combinedClickable(
//                onClick = {
//                    println("--Clicked")
//                },
//                onDoubleClick = {
//                    println("--Double Clicked")
//                },
//                onLongClick = {
//                    println("--Long Clicked")
//                }
//            )
    ) {
        Text(
            modifier = ToolsColumnModifier.ToolNo.modifier,
            textAlign = TextAlign.Center,
            text = item.toolNo.toString()
        )
        VerticalDivider()
        Column(
            modifier = ToolsColumnModifier.Offset.modifier
        ) {
            LabelWithValue("X:", item.xOffset.toFixedDigitsString())
            LabelWithValue("Z:", item.zOffset.toFixedDigitsString())
        }
        VerticalDivider()
//        Column(
//            modifier = ToolsColumnModifier.Offset.modifier
//        ) {
//            LabelWithValue("X:", item.xWear.toFixedDigitsString())
//            LabelWithValue("Z:", item.zWear.toFixedDigitsString())
//        }
//        VerticalDivider()
        Text(
            modifier = ToolsColumnModifier.TipRadius.modifier,
            textAlign = TextAlign.Center,
            text = item.tipRadius.toFixedDigitsString()
        )
        VerticalDivider()
        Text(
            modifier = ToolsColumnModifier.Orientation.modifier,
            textAlign = TextAlign.Center,
            text = item.orientation.angle.toString()
        )
        VerticalDivider()
        IconButton(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            onClick = {
                onEditClicked.invoke(item)
            }) {
            Icon(Icons.Default.Edit, contentDescription = "")
        }
        VerticalDivider()
        IconButton(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            enabled = isCurrent.not(),
            onClick = {
                onDeleteClicked.invoke(item)
            }) {
            Icon(Icons.Default.Delete, contentDescription = "")
        }
        VerticalDivider()
        IconButton(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            enabled = isCurrent.not(),
            onClick = {
                onLoadClicked.invoke(item)
            }) {
            Icon(Icons.Default.ExitToApp, contentDescription = "")
        }
        VerticalDivider()
    }
}

