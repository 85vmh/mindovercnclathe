package ui.screen.programs.root

import cafe.adriel.voyager.core.model.StateScreenModel
import com.mindovercnc.editor.EditorLoader
import com.mindovercnc.repository.FileSystemRepository
import kotlinx.coroutines.flow.update
import mu.KotlinLogging
import okio.FileSystem
import okio.Path
import usecase.BreadCrumbDataUseCase
import usecase.FileSystemDataUseCase

class ProgramsRootScreenModel(
  fileSystemRepository: FileSystemRepository,
  private val fileSystem: FileSystem,
  private val editorLoader: EditorLoader,
  private val fileSystemDataUseCase: FileSystemDataUseCase,
  private val breadCrumbDataUseCase: BreadCrumbDataUseCase
) : StateScreenModel<ProgramsState>(ProgramsState()) {

  private val logger = KotlinLogging.logger("ProgramsRootScreenModel")

  init {
    val path = fileSystemRepository.getNcRootAppFile()
    logger.info("NC Root App File path $path")
    setCurrentFolder(path)
  }

  fun showError(error: String) {
    mutableState.update { it.copy(error = error) }
  }

  fun clearError() {
    mutableState.update { it.copy(error = null) }
  }

  private fun setCurrentFolder(file: Path) {
    logger.info("Setting current folder to $file")
    mutableState.update {
      val fileSystemData =
        with(fileSystemDataUseCase) { file.toFileSystemData(onItemClick = ::selectItem) }
      val breadCrumbData =
        with(breadCrumbDataUseCase) { file.toBreadCrumbData(onItemClick = ::loadFolderContents) }

      it.copy(breadCrumbData = breadCrumbData, fileSystemData = fileSystemData)
    }
  }

  private fun setCurrentFile(file: Path?) {
    logger.info("Setting current file to $file")
    mutableState.update {
      it.copy(
        editor = if (file != null) editorLoader.loadEditor(file) else null,
      )
    }
  }

  fun selectItem(item: Path) {
    val metadata = fileSystem.metadata(item)
    when {
      metadata.isDirectory -> {
        println("---Folder clicked: $item")
        loadFolderContents(item)
      }
      metadata.isRegularFile -> {
        setCurrentFile(item)
      }
    }
  }

  fun loadFolderContents(file: Path) {
    setCurrentFolder(file)
    setCurrentFile(null)
  }
}
