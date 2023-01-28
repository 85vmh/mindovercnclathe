package ui.screen.programs.root

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import components.breadcrumb.BreadcrumbView
import components.filesystem.FileSystemView
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.HorizontalSplitPane
import org.jetbrains.compose.splitpane.rememberSplitPaneState
import org.jetbrains.skiko.Cursor
import screen.composables.common.Settings
import screen.composables.editor.EditorEmptyView
import screen.composables.editor.EditorView

@OptIn(ExperimentalSplitPaneApi::class)
@Composable
fun ProgramsScreenUi(state: ProgramsState, modifier: Modifier = Modifier) {
  val settings = Settings()

  Column(modifier = modifier) {
    BreadcrumbView(
      data = state.breadCrumbData,
      modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
      contentPadding = PaddingValues(horizontal = 8.dp)
    )
    Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
    val splitPaneState = rememberSplitPaneState(initialPositionPercentage = 0.5f)
    HorizontalSplitPane(splitPaneState = splitPaneState) {
      // file explorer
      first(120.dp) {
        FileSystemView(data = state.fileSystemData, modifier = Modifier.fillMaxSize())
      }

      // editor
      second(120.dp) {
        val editorModifier = Modifier.fillMaxSize()
        if (state.editor != null) {
          EditorView(model = state.editor, settings = settings, modifier = editorModifier)
        } else {
          EditorEmptyView(modifier = editorModifier)
        }
      }

      // divider
      splitter {
        visiblePart {
          Box(
            Modifier.width(DividerDefaults.Thickness)
              .fillMaxHeight()
              .background(DividerDefaults.color)
          )
        }
        handle {
          Modifier.markAsHandle()
            .cursorForHorizontalResize()
            .background(SolidColor(Color.Gray), alpha = 0.50f)
            .width(16.dp)
            .fillMaxHeight()
        }
      }
    }
  }
}

private fun Modifier.cursorForHorizontalResize(): Modifier =
  pointerHoverIcon(PointerIcon(Cursor(Cursor.E_RESIZE_CURSOR)))
