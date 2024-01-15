package com.mindovercnc.data.linuxcnc.remote

import com.mindovercnc.dispatchers.NewSingleThreadDispatcher
import com.mindovercnc.dispatchers.createScope
import com.mindovercnc.data.linuxcnc.SystemMessageRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import mu.KotlinLogging
import ro.dragossusi.proto.linuxcnc.LinuxCncClient
import ro.dragossusi.proto.linuxcnc.ReadErrorRequest
import ro.dragossusi.proto.linuxcnc.status.SystemMessage

/** Remote implementation for [SystemMessageRepository]. */
class SystemMessageRepositoryRemote
constructor(
    private val linuxCncGrpc: LinuxCncClient, newSingleThreadDispatcher: NewSingleThreadDispatcher
) : SystemMessageRepository {

    private val scope = newSingleThreadDispatcher.createScope()

    override val systemMessageFlow: Flow<SystemMessage> = flow {
        while (true) {
            val request = ReadErrorRequest()
            try {
                val systemMessage = linuxCncGrpc.ReadError().execute(request)
                emit(systemMessage)
            } catch (e: Exception) {
                LOG.error(e) { "Failed to get error" }
            }
            delay(100L)
        }
    }.shareIn(scope, started = SharingStarted.Lazily, replay = 1)

    companion object {
        private val LOG = KotlinLogging.logger("SystemMessageRepositoryRemote")
    }
}
