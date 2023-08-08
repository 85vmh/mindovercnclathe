package ui.screen.tools.root.tabs.lathetool

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mindovercnc.model.LatheTool
import di.rememberScreenModel
import org.kodein.di.bindProvider
import ui.screen.manual.Manual

class AddEditLatheToolScreen(
    private val latheTool: LatheTool? = null,
    private val onChanges: () -> Unit
) :
    Manual(
        when (latheTool) {
            null -> "Add Lathe Tool"
            else -> "Edit Lathe Tool #${latheTool.toolId}"
        }
    ) {

    @Composable
    override fun Actions() {
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
