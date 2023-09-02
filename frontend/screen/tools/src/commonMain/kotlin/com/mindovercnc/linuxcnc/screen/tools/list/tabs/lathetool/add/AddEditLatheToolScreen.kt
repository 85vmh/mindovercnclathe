package com.mindovercnc.linuxcnc.screen.tools.list.tabs.lathetool.add

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
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.lathetool.ui.AddEditLatheToolContent
import com.mindovercnc.linuxcnc.tools.model.LatheTool
import org.kodein.di.bindProvider

class AddEditLatheToolScreen(
    private val latheTool: LatheTool? = null,
    private val onChanges: () -> Unit
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
        val screenModel: AddEditLatheToolScreenModel =
            when (latheTool) {
                null -> rememberScreenModel()
                else -> rememberScreenModel { bindProvider { latheTool } }
            }

        val state by screenModel.state.collectAsState()

        AddEditLatheToolContent(
            state = state,
            onToolId = screenModel::setToolId,
            onToolType = screenModel::setToolType,
            onCuttingInsert = screenModel::setCuttingInsert,
            onToolOrientation = screenModel::setToolOrientation,
            onToolDiameter = screenModel::setToolDiameter,
            onBackAngle = screenModel::setBackAngle,
            onFrontAngle = screenModel::setFrontAngle,
            onSpindleDirection = screenModel::setSpindleDirection,
            onMinBoreDiameter = screenModel::setMinBoreDiameter,
            onMaxZDepth = screenModel::setMaxZDepth,
            onMaxXDepth = screenModel::setMaxXDepth,
            onBladeWidth = screenModel::setBladeWidth,
            onMinThreadPitch = screenModel::setMinThreadPitch,
            onMaxThreadPitch = screenModel::setMaxThreadPitch
        )
    }
}

private fun createTitle(latheTool: LatheTool?) =
    when (latheTool) {
        null -> "Add Lathe Tool"
        else -> "Edit Lathe Tool #${latheTool.toolId}"
    }
