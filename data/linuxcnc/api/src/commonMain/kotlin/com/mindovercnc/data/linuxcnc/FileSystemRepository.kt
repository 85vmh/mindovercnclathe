package com.mindovercnc.data.linuxcnc

import com.mindovercnc.data.linuxcnc.model.FileResponse
import okio.Path

interface FileSystemRepository {
    fun getNcRootAppFile(): Path

    fun getFilesInPath(path: Path): List<FileResponse>

    suspend fun writeProgramLines(lines: List<String>, programName: String)
}