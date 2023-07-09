package usecase

import com.mindovercnc.model.extension
import components.filesystem.FileSystemData
import components.filesystem.FileSystemItemData
import okio.FileSystem
import okio.Path
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

class FileSystemDataUseCase constructor(private val fileSystem: FileSystem) {

    // TODO change with an expect/actual for MPP
    private val clipboard = Toolkit.getDefaultToolkit().systemClipboard

    fun Path.toFileSystemData(onItemClick: (Path) -> Unit): FileSystemData {
        val items =
            fileSystem
                .list(this)
                .filter { it.isDisplayable() }
                .map { item ->
                    val metadata = fileSystem.metadata(item)
                    FileSystemItemData(
                        title = item.name,
                        isDirectory = metadata.isDirectory,
                        lastModified = metadata.lastModifiedAtMillis,
                        onClick = { onItemClick(item) },
                        onCopy = {
                            clipboard.setContents(StringSelection(item.toString()), null)
                        }
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
