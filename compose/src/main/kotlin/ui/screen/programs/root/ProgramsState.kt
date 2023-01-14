package ui.screen.programs.root

import androidx.compose.runtime.Stable
import components.breadcrumb.BreadCrumbData
import components.filesystem.FileSystemData
import com.mindovercnc.editor.Editor

@Stable
data class ProgramsState(
    val breadCrumbData: BreadCrumbData = BreadCrumbData.Empty,
    val fileSystemData: FileSystemData = FileSystemData.Empty,
    val editor: Editor? = null,
    val error: String? = null
)
