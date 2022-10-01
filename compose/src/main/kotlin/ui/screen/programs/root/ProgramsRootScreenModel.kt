package ui.screen.programs.root

import cafe.adriel.voyager.core.model.StateScreenModel
import com.mindovercnc.repository.FileSystemRepository
import kotlinx.coroutines.flow.update
import screen.composables.editor.Editor
import java.io.File

class ProgramsRootScreenModel(
    fileSystemRepository: FileSystemRepository,
) : StateScreenModel<ProgramsRootScreenModel.State>(
    State(
        currentDir = fileSystemRepository.getNcRootAppFile()
    )
) {

    data class State(
        val currentDir: File,
        val editor: Editor? = null,
        val error: String? = null
    )

    init {
        setCurrentFolder(fileSystemRepository.getNcRootAppFile())
    }

    fun showError(error: String) {
        mutableState.update {
            it.copy(error = error)
        }
    }

    fun clearError() {
        mutableState.update {
            it.copy(error = null)
        }
    }

    private fun setCurrentFolder(file: File) {
        mutableState.update {
            it.copy(currentDir = file)
        }
    }

    private fun setCurrentFile(file: File?) {
        mutableState.update {
            it.copy(
                editor = if (file != null) Editor(file)
                else null,
            )
        }
    }

    fun selectItem(item: File) {
        when {
            item.isDirectory -> {
                println("---Folder clicked: ${item.path}")
                loadFolderContents(item)
            }
            item.isFile -> {
                setCurrentFile(File(item.path))
            }
        }
    }

    fun loadFolderContents(file: File) {
        setCurrentFolder(file)
        setCurrentFile(null)
    }

}