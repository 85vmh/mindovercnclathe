package com.mindovercnc.data.linuxcnc.local

import com.mindovercnc.data.linuxcnc.FileSystemRepository
import com.mindovercnc.data.linuxcnc.model.FileResponse
import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.model.extension
import kotlinx.datetime.Instant
import okio.FileSystem
import okio.Path

/** Implementation for [FileSystemRepository]. */
class FileSystemRepositoryLocal(
    ioDispatcher: IoDispatcher,
    private val ncProgramsDir: Path,
    private val fileSystem: FileSystem
) : FileSystemRepository {

    override fun getNcRootAppFile(): Path {
        return ncProgramsDir
    }

    override fun getFilesInPath(path: Path): List<FileResponse> {
        return fileSystem
            .list(path)
            .filter { it.isDisplayable() }
            .map { item ->
                val metadata = fileSystem.metadata(item)
                val lastModified = metadata.lastModifiedAtMillis?.let { Instant.fromEpochMilliseconds(it) }
                FileResponse(
                    name = item.name,
                    isDirectory = metadata.isDirectory,
                    lastModified = lastModified,
                    path = item
                )
            }
    }

    override suspend fun writeProgramLines(lines: List<String>, programName: String) {
        val conversationalFolder = ncProgramsDir.div("conversational")
        if (!fileSystem.exists(conversationalFolder)) {
            fileSystem.createDirectory(conversationalFolder)
        }
        val programFile = conversationalFolder.div(programName)

        fileSystem.write(programFile) { lines.forEach { line -> writeUtf8(line) } }
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
