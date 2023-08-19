package com.mindovercnc.data.linuxcnc.local

import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.data.linuxcnc.FileSystemRepository
import okio.FileSystem
import okio.Path

/** Implementation for [FileSystemRepository]. */
class FileSystemRepositoryImpl(
    ioDispatcher: IoDispatcher,
    private val ncProgramsDir: Path,
    private val fileSystem: FileSystem
) : FileSystemRepository {

    override fun getNcRootAppFile(): Path {
        return ncProgramsDir
    }

    override suspend fun writeProgramLines(lines: List<String>, programName: String) {
        val conversationalFolder = ncProgramsDir.div("conversational")
        if (!fileSystem.exists(conversationalFolder)) {
            fileSystem.createDirectory(conversationalFolder)
        }
        val programFile = conversationalFolder.div(programName)

        fileSystem.write(programFile) { lines.forEach { line -> writeUtf8(line) } }
    }
}
