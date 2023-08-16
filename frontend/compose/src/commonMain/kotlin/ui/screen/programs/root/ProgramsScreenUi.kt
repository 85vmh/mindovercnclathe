package ui.screen.programs.root

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import components.breadcrumb.BreadcrumbView
import components.filesystem.FileSystemView
import editor.EditorEmptyView
import editor.EditorSettings
import editor.EditorView
import com.mindovercnc.linuxcnc.widgets.VerticalDivider

@Composable
fun ProgramsScreenUi(state: ProgramsState, modifier: Modifier = Modifier) {
    val settings = EditorSettings()

    Column(modifier = modifier) {
        BreadcrumbView(
            data = state.breadCrumbData,
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        )
        Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
        Row {
            val itemModifier = Modifier.fillMaxSize()
                .weight(1f)
                .widthIn(min = 120.dp)

            // file explorer
            FileSystemView(data = state.fileSystemData, modifier = itemModifier)

            // divider
            VerticalDivider()

            // editor
            if (state.editor != null) {
                EditorView(model = state.editor, settings = settings, modifier = itemModifier)
            } else {
                EditorEmptyView(modifier = itemModifier)
            }
        }
    }
}

