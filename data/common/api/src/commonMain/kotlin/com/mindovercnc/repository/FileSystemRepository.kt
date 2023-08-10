package com.mindovercnc.repository

import okio.Path

interface FileSystemRepository {
    fun getNcRootAppFile(): Path

    suspend fun writeProgramLines(lines: List<String>, programName: String)
}