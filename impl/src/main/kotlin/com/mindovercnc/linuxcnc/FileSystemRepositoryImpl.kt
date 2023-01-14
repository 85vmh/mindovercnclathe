package com.mindovercnc.linuxcnc

import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.dispatchers.createScope
import com.mindovercnc.repository.FileSystemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.FileSystem
import okio.Path

/** Implementation for [FileSystemRepository]. */
class FileSystemRepositoryImpl(
  ioDispatcher: IoDispatcher,
  private val ncProgramsDir: Path,
  private val fileSystem: FileSystem
) : FileSystemRepository {

  private val scope = ioDispatcher.createScope()

  override fun getNcRootAppFile(): Path {
    return ncProgramsDir
  }

  override fun writeProgramLines(lines: List<String>, programName: String) {
    scope.launch(Dispatchers.IO) {
      val conversationalFolder = ncProgramsDir.div("conversational")
      if (!fileSystem.exists(conversationalFolder)) {
        fileSystem.createDirectory(conversationalFolder)
      }
      val programFile = conversationalFolder.div(programName)

      fileSystem.write(programFile) { lines.forEach { line -> writeUtf8(line) } }
    }
  }
}
