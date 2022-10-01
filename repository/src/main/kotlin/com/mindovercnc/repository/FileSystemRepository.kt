package com.mindovercnc.repository

import java.io.File

interface FileSystemRepository {
    fun getNcRootAppFile() : File

    fun writeProgramLines(lines: List<String>, programName: String)
}