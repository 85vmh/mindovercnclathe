package ui.screen.programs.root

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import di.rememberScreenModel
import ui.screen.programs.Programs
import ui.screen.programs.programloaded.ProgramLoadedScreen
import ui.widget.ErrorSnackbar

class ProgramsRootScreen : Programs("Programs") {

  @Composable
  override fun Fab() {
    val screenModel = rememberScreenModel<ProgramsRootScreenModel>()
    val state by screenModel.state.collectAsState()
    val navigator = LocalNavigator.currentOrThrow

    state.editor?.let { editor ->
      ExtendedFloatingActionButton(
        text = { Text("Load Program") },
        onClick = { navigator.push(ProgramLoadedScreen(editor.file)) },
        icon = {
          Icon(
            Icons.Default.ExitToApp,
            contentDescription = "",
          )
        }
      )
    }
  }

  @Composable
  override fun Content() {
    val screenModel = rememberScreenModel<ProgramsRootScreenModel>()
    val state by screenModel.state.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
      ProgramsScreenUi(state)
      ErrorSnackbar(
        state.error,
        onDismiss = screenModel::clearError,
        modifier = Modifier.align(Alignment.BottomEnd)
      )
    }
  }
}
