package com.mindovercnc.linuxcnc.screen.programs.picker

import androidx.compose.runtime.Stable
import components.breadcrumb.BreadCrumbData
import com.mindovercnc.editor.Editor
import components.filesystem.FileSystemData

@Stable
data class ProgramPickerState(
    val breadCrumbData: BreadCrumbData = BreadCrumbData.Empty,
    val fileSystemData: FileSystemData = FileSystemData.Empty,
    val editor: Editor? = null,
    val error: String? = null
)
