package com.mindovercnc.linuxcnc.screen.programs.root

import com.mindovercnc.linuxcnc.screen.AppScreenComponent
import okio.Path

interface ProgramsRootComponent : AppScreenComponent<ProgramsState> {
    fun showError(error: String)
    fun clearError()
    fun selectItem(item: Path)
    fun loadFolderContents(file: Path)
}