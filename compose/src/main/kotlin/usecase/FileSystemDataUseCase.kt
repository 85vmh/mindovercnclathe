package usecase

import com.mindovercnc.model.extension
import components.filesystem.FileSystemData
import components.filesystem.FileSystemItemData
import okio.FileSystem
import okio.Path

class FileSystemDataUseCase constructor(private val fileSystem: FileSystem) {

    fun Path.toFileSystemData(onItemClick: (Path) -> Unit): FileSystemData {
        val items =
            fileSystem
                .list(this)
                .filter { it.isDisplayable() }
                .map {
                    val metadata = fileSystem.metadata(it)
                    FileSystemItemData(
                        title = it.name,
                        isDirectory = metadata.isDirectory,
                        onClick = { onItemClick(it) },
                        lastModified = metadata.lastModifiedAtMillis
                    )
                }
                .sortedWith(compareBy({ it.isDirectory }, { it.title }))
        return FileSystemData(items)
    }

    private fun Path.isDisplayable(): Boolean {
        val metadata = fileSystem.metadata(this)
        return if (metadata.isDirectory) {
            !toFile().isHidden
        } else {
            !toFile().isHidden && extension.equals("ngc", true)
        }
    }
}
