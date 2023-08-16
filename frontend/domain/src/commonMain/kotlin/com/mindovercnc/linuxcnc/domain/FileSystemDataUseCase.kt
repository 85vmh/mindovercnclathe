package com.mindovercnc.linuxcnc.domain

import clipboard.Clipboard
import com.mindovercnc.model.extension
import components.filesystem.FileSystemData
import components.filesystem.FileSystemItemData
import kotlinx.datetime.Instant
import okio.FileSystem
import okio.Path

class FileSystemDataUseCase constructor(
    private val fileSystem: FileSystem
) {

    fun Path.toFileSystemData(onItemClick: (Path) -> Unit): FileSystemData {
        val items = fileSystem
            .list(this)
            .filter { it.isDisplayable() }
            .map { item ->
                val metadata = fileSystem.metadata(item)
                val lastModified = metadata.lastModifiedAtMillis?.let { Instant.fromEpochMilliseconds(it) }
                FileSystemItemData(
                    title = item.name,
                    isDirectory = metadata.isDirectory,
                    lastModified = lastModified,
                    onClick = { onItemClick(item) },
                    onCopy = { Clipboard.write(item.toString()) }
                )
            }
            .sortedWith(compareBy({ it.isDirectory }, { it.title }))
        return FileSystemData(items)
    }

    private fun Path.isDisplayable(): Boolean {
        val metadata = fileSystem.metadata(this)
        // todo change to real implementation
        val isHidden = false // toFile().isHidden
        return if (metadata.isDirectory) {
            !isHidden
        } else {
            !isHidden && extension.equals("ngc", true)
        }
    }
}
