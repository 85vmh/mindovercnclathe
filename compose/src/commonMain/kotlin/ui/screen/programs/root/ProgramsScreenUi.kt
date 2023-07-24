package ui.screen.programs.root

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import components.breadcrumb.BreadcrumbView
import components.filesystem.FileSystemView
import screen.composables.VerticalDivider
import screen.composables.common.Settings
import screen.composables.editor.EditorEmptyView
import screen.composables.editor.EditorView

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

