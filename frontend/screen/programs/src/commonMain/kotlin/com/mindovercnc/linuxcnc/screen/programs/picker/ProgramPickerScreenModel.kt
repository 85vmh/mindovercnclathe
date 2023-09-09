package com.mindovercnc.linuxcnc.screen.programs.picker

import com.arkivanov.decompose.ComponentContext
import com.mindovercnc.data.linuxcnc.FileSystemRepository
import com.mindovercnc.editor.EditorLoader
import com.mindovercnc.linuxcnc.domain.BreadCrumbDataUseCase
import com.mindovercnc.linuxcnc.domain.FileSystemDataUseCase
import com.mindovercnc.linuxcnc.screen.BaseScreenModel
import kotlinx.coroutines.flow.update
import mu.KotlinLogging
import okio.FileSystem
import okio.Path
import org.kodein.di.DI
import org.kodein.di.instance

class ProgramPickerScreenModel(di: DI, componentContext: ComponentContext) :
    BaseScreenModel<ProgramPickerState>(ProgramPickerState(), componentContext),
    ProgramPickerComponent {

    private val fileSystemRepository: FileSystemRepository by di.instance()
    private val fileSystem: FileSystem by di.instance()
    private val editorLoader: EditorLoader by di.instance()
    private val fileSystemDataUseCase: FileSystemDataUseCase by di.instance()
    private val breadCrumbDataUseCase: BreadCrumbDataUseCase by di.instance()

    private val logger = KotlinLogging.logger("ProgramsRootScreenModel")

    init {
        val path = fileSystemRepository.getNcRootAppFile()
        logger.info { "NC Root App File path $path" }
        setCurrentFolder(path)
    }

    override fun showError(error: String) {
        mutableState.update { it.copy(error = error) }
    }

    override fun clearError() {
        mutableState.update { it.copy(error = null) }
    }

    override fun selectItem(item: Path) {
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

    override fun loadFolderContents(file: Path) {
        setCurrentFolder(file)
        setCurrentFile(null)
    }

    private fun setCurrentFolder(file: Path) {
        logger.info { "Setting current folder to $file" }
        mutableState.update {
            val fileSystemData =
                with(fileSystemDataUseCase) { file.toFileSystemData(onItemClick = ::selectItem) }
            val breadCrumbData =
                with(breadCrumbDataUseCase) {
                    file.toBreadCrumbData(onItemClick = ::loadFolderContents)
                }

            it.copy(breadCrumbData = breadCrumbData, fileSystemData = fileSystemData)
        }
    }

    private fun setCurrentFile(file: Path?) {
        logger.info { "Setting current file to $file" }
        mutableState.update {
            it.copy(
                editor = if (file != null) editorLoader.loadEditor(file) else null,
            )
        }
    }
}
