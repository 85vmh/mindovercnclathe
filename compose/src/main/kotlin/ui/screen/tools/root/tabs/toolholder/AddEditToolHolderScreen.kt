package ui.screen.tools.root.tabs.toolholder

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mindovercnc.model.LatheTool
import com.mindovercnc.model.ToolHolder
import com.mindovercnc.model.ToolHolderType
import di.rememberScreenModel
import extensions.draggableScroll
import org.kodein.di.bindProvider
import screen.uimodel.InputType
import ui.screen.manual.Manual
import ui.screen.tools.root.tabs.LatheToolView
import ui.widget.ValueSetting

class AddEditHolderScreen(
    private val toolHolder: ToolHolder? = null,
    private val onChanges: () -> Unit
) : Manual(
    when (toolHolder) {
        null -> "Add Tool Holder"
        else -> "Edit Tool Holder #${toolHolder.holderNumber}"
    }
) {

    @Composable
    override fun Actions() {
        val screenModel: AddEditToolHolderScreenModel = when (toolHolder) {
            null -> rememberScreenModel()
            else -> rememberScreenModel { bindProvider { toolHolder } }
        }

        val navigator = LocalNavigator.currentOrThrow
        IconButton(
            modifier = iconButtonModifier,
            onClick = {
                screenModel.applyChanges()
                onChanges.invoke()
                navigator.pop()
            }) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "",
            )
        }
    }

    @Composable
    override fun Content() {
        val screenModel: AddEditToolHolderScreenModel = when (toolHolder) {
            null -> rememberScreenModel()
            else -> rememberScreenModel { bindProvider { toolHolder } }
        }

        val state by screenModel.state.collectAsState()
        AddEditHolderContent(
            state,
            onHolderNumber = screenModel::setHolderNumber,
            onHolderType = screenModel::setHolderType,
            onLatheTool = screenModel::setLatheTool
        )
    }
}

@Composable
private fun AddEditHolderContent(
    state: AddEditToolHolderScreenModel.State,
    onHolderNumber: (Int) -> Unit,
    onHolderType: (ToolHolderType) -> Unit,
    onLatheTool: (LatheTool) -> Unit
) {
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            ToolHolderType.values().forEach {
                ToolHolderView(
                    type = it,
                    isSelected = it == state.type
                ) {
                    onHolderType(it)
                }
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
                modifier = Modifier.width(250.dp)
            )
        }

        val scrollState = rememberLazyListState()

        LazyColumn(
            modifier = Modifier.draggableScroll(scrollState, scope),
            state = scrollState
        ) {
            itemsIndexed(state.latheToolsList) { index, item ->
                LatheToolView(
                    latheTool = item,
                    isSelected = item == state.latheTool
                ) {
                    onLatheTool.invoke(it)
                }
                Divider(color = Color.LightGray, thickness = 0.5.dp)
            }
        }
    }
}

@Composable
fun ToolHolderView(
    type: ToolHolderType,
    isSelected: Boolean,
    onClick: (ToolHolderType) -> Unit
) {
    val imageResource = when (type) {
        ToolHolderType.Generic -> "multifix_generic"
        ToolHolderType.Boring -> "multifix_bore"
        ToolHolderType.DrillHolder -> "multifix_drill"
        ToolHolderType.Parting -> "multifix_part"
    }

    Surface(
        modifier = Modifier
            .padding(16.dp)
            .shadow(8.dp, shape = RoundedCornerShape(8.dp))
            .clickable {
                onClick(type)
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                modifier = Modifier.size(120.dp),
                painter = painterResource(imageResource),
                contentDescription = ""
            )
            Text(
                modifier = Modifier,
                text = type.name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
@Preview
fun AddEditHolderContentPreview() {
    AddEditHolderContent(
        AddEditToolHolderScreenModel.State(),
        {},
        {},
        {}
    )
}