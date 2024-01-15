package com.mindovercnc.data.linuxcnc.remote

import com.mindovercnc.data.linuxcnc.FileSystemRepository
import com.mindovercnc.data.linuxcnc.model.FileResponse
import mu.KotlinLogging
import okio.Path
import okio.Path.Companion.toPath

class FileSystemRepositoryRemote : FileSystemRepository {
    override fun getNcRootAppFile(): Path {
        // TODO
        return ".".toPath()
    }

    override fun getFilesInPath(path: Path): List<FileResponse> {
        // TODO
        return emptyList()
    }

    override suspend fun writeProgramLines(lines: List<String>, programName: String) {
        LOG.warn { "Invoked writeProgramLines" }
    }

    companion object {
        private val LOG = KotlinLogging.logger("FileSystemRepositoryRemote")
    }
}