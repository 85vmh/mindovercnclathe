package com.mindovercnc.linuxcnc

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.nio.file.FileSystems
import java.nio.file.Paths
import java.nio.file.StandardWatchEventKinds

object FileWatcher {
    fun watchChanges(scope: CoroutineScope, filePath: String, emitWhenCalled: Boolean = false, onChanged: () -> Unit) {
        val dir = filePath.substring(0, filePath.lastIndexOf("/") + 1)
        val file = filePath.substring(filePath.lastIndexOf("/") + 1)
        val watchService = FileSystems.getDefault().newWatchService()
        Paths.get(dir).register(watchService, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_CREATE)

        scope.launch(Dispatchers.IO) {
            if (emitWhenCalled) {
                onChanged.invoke()
            }
            var poll = true
            while (poll) {
                val key = watchService.take()
                for (event in key.pollEvents()) {
                    if (event.context().toString() == file) {
                        //println("---File changed: $filePath")
                        onChanged.invoke()
                    }
                }
                poll = key.reset()
            }
        }
    }
}