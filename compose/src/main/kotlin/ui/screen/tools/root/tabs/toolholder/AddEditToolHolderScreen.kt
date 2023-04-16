package ui.screen.tools.root.tabs.toolholder

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mindovercnc.model.ToolHolder
import di.rememberScreenModel
import org.kodein.di.bindProvider
import ui.screen.manual.Manual

class AddEditHolderScreen(
  private val toolHolder: ToolHolder? = null,
  private val onChanges: () -> Unit
) :
  Manual(
    when (toolHolder) {
      null -> "Add Tool Holder"
      else -> "Edit Tool Holder #${toolHolder.holderNumber}"
    }
  ) {

  @Composable
  override fun Actions() {
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
