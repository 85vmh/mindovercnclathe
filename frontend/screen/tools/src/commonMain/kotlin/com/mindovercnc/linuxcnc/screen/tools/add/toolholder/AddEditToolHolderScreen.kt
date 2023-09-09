package com.mindovercnc.linuxcnc.screen.tools.add.toolholder

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mindovercnc.linuxcnc.screen.rememberScreenModel
import com.mindovercnc.linuxcnc.screen.tools.Tools
import com.mindovercnc.linuxcnc.screen.tools.add.toolholder.ui.AddEditToolHolderScreenUi
import com.mindovercnc.linuxcnc.tools.model.ToolHolder
import org.kodein.di.bindProvider

class AddEditToolHolderScreen(
    private val toolHolder: ToolHolder? = null,
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
                navigator.pop()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
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

        AddEditToolHolderScreenUi(screenModel, Modifier.fillMaxSize())
    }

    companion object {

        private fun createTitle(toolHolder: ToolHolder?) =
            when (toolHolder) {
                null -> "Add Tool Holder"
                else -> "Edit Tool Holder #${toolHolder.holderNumber}"
            }
    }
}
