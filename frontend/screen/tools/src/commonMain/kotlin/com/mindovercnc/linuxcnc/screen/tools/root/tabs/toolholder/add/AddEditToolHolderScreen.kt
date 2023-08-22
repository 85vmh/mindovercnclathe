package com.mindovercnc.linuxcnc.screen.tools.root.tabs.toolholder.add

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mindovercnc.linuxcnc.screen.rememberScreenModel
import com.mindovercnc.linuxcnc.screen.tools.Tools
import com.mindovercnc.linuxcnc.tools.model.ToolHolder
import org.kodein.di.bindProvider

class AddEditHolderScreen(
    private val toolHolder: ToolHolder? = null,
    private val onChanges: () -> Unit
) : Tools(createTitle(toolHolder)) {

    @Composable
    override fun RowScope.Actions() {
        val screenModel: AddEditToolHolderScreenModel =
            when (toolHolder) {
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
            }
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "",
            )
        }
    }

    @Composable
    override fun Content() {
        val screenModel: AddEditToolHolderScreenModel =
            when (toolHolder) {
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

private fun createTitle(toolHolder: ToolHolder?) =
    when (toolHolder) {
        null -> "Add Tool Holder"
        else -> "Edit Tool Holder #${toolHolder.holderNumber}"
    }
