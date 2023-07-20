package com.mindovercnc.repository

import okio.Path

interface FileSystemRepository {
    fun getNcRootAppFile() : Path

    fun writeProgramLines(lines: List<String>, programName: String)
}