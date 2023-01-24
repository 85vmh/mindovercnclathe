package ui.screen.programs.root

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import components.breadcrumb.BreadcrumbView
import components.filesystem.FileSystemView
import di.rememberScreenModel
import screen.composables.VerticalDivider
import screen.composables.common.Settings
import screen.composables.editor.EditorEmptyView
import screen.composables.editor.EditorView
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

    val settings = Settings()

    Box {
      Column {
        BreadcrumbView(
          data = state.breadCrumbData,
          modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
          contentPadding = PaddingValues(horizontal = 8.dp)
        )
        Divider(color = Color.LightGray, thickness = 1.dp)
        Row(modifier = Modifier) {
          // file explorer
          FileSystemView(
            data = state.fileSystemData,
            modifier = Modifier.fillMaxHeight().width(400.dp)
          )

          // divider
          VerticalDivider()

          // editor
          if (state.editor != null) {
            Column(Modifier.weight(1f)) {
              EditorView(
                model = state.editor!!,
                settings = settings,
                modifier = Modifier.fillMaxSize()
              )
            }
          } else {
            EditorEmptyView()
          }
        }
      }
      ErrorSnackbar(
        state.error,
        onDismiss = screenModel::clearError,
        modifier = Modifier.align(Alignment.BottomEnd)
      )
    }
  }
}
