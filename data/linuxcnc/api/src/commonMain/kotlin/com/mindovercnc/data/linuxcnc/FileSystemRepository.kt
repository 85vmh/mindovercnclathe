package com.mindovercnc.data.linuxcnc

import okio.Path

interface FileSystemRepository {
    fun getNcRootAppFile(): Path

    suspend fun writeProgramLines(lines: List<String>, programName: String)
}