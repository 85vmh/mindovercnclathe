package com.mindovercnc.linuxcnc

import java.nio.file.FileSystems
import java.nio.file.Paths
import java.nio.file.StandardWatchEventKinds
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object FileWatcher {

  fun watchChanges(
    filePath: String,
    emitWhenCalled: Boolean = false,
  ): Flow<Unit> = flow {
    val dir = filePath.substring(0, filePath.lastIndexOf("/") + 1)
    val file = filePath.substring(filePath.lastIndexOf("/") + 1)
    val watchService = FileSystems.getDefault().newWatchService()
    val path = Paths.get(dir)

    val watchKey =
      path.register(
        watchService,
        StandardWatchEventKinds.ENTRY_MODIFY,
        StandardWatchEventKinds.ENTRY_CREATE
      )
    if (emitWhenCalled) {
      emit(Unit)
    }
    var poll = true
    while (poll) {
      val key = watchService.take()
      key.pollEvents().forEach { event ->
        if (event.context().toString() == file) {
          // println("---File changed: $filePath")
          emit(Unit)
        }
      }
      poll = key.reset()
    }
  }
}
