package com.mindovercnc.data.linuxcnc.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.nio.file.FileSystems
import java.nio.file.Paths
import java.nio.file.StandardWatchEventKinds

object FileWatcher {

    fun watchChanges(
        filePath: String,
        emitWhenCalled: Boolean = false,
    ): Flow<Unit> = flow {
        val dir = filePath.substring(0, filePath.lastIndexOf("/") + 1)
        val file = filePath.substring(filePath.lastIndexOf("/") + 1)
        val watchService = FileSystems.getDefault().newWatchService()
        val path = Paths.get(dir)

        @Suppress("UNUSED_VARIABLE")
        val watchKey = path.register(
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
        .flowOn(Dispatchers.IO)
}
