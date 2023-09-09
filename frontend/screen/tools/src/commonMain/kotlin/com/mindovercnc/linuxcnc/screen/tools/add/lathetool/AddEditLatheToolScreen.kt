package com.mindovercnc.linuxcnc.screen.tools.add.lathetool

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
import com.mindovercnc.linuxcnc.screen.tools.add.lathetool.ui.AddEditLatheToolScreenUi
import com.mindovercnc.linuxcnc.tools.model.LatheTool
import org.kodein.di.bindProvider

class AddEditLatheToolScreen(
    private val latheTool: LatheTool? = null,
) : Tools(createTitle(latheTool)) {

    @Composable
    override fun RowScope.Actions() {
        val screenModel: AddEditLatheToolScreenModel =
            when (latheTool) {
                null -> rememberScreenModel()
                else -> rememberScreenModel { bindProvider { latheTool } }
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
        val screenModel: AddEditLatheToolScreenModel =
            when (latheTool) {
                null -> rememberScreenModel()
                else -> rememberScreenModel { bindProvider { latheTool } }
            }

        AddEditLatheToolScreenUi(screenModel, Modifier.fillMaxSize())
    }

    companion object {

        private fun createTitle(latheTool: LatheTool?) =
            when (latheTool) {
                null -> "Add Lathe Tool"
                else -> "Edit Lathe Tool #${latheTool.toolId}"
            }
    }
}
