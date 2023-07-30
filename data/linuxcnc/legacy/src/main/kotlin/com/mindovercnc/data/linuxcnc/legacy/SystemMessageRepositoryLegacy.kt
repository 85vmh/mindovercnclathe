package com.mindovercnc.data.linuxcnc.legacy

import com.mindovercnc.dispatchers.NewSingleThreadDispatcher
import com.mindovercnc.dispatchers.createScope
import com.mindovercnc.data.linuxcnc.SystemMessageRepository
import com.mindovercnc.linuxcnc.ErrorReader
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import mu.KotlinLogging
import ro.dragossusi.proto.linuxcnc.LinuxCncClient
import ro.dragossusi.proto.linuxcnc.ReadErrorRequest
import ro.dragossusi.proto.linuxcnc.status.SystemMessage

// TODO split into legacy and remote
/** Implementation for [SystemMessageRepository]. */
class SystemMessageRepositoryLegacy
constructor(
    private val errorReader: ErrorReader
    newSingleThreadDispatcher: NewSingleThreadDispatcher
) : SystemMessageRepository {

    private val logger = KotlinLogging.logger("SystemMessageRepositoryImpl")

    private val scope = newSingleThreadDispatcher.createScope()

    override val systemMessageFlow: Flow<SystemMessage> =
        errorReader.refresh(10L).filterNotNull()
        flow {
        while (true) {
            val request = ReadErrorRequest()
            try {
                val systemMessage = linuxCncGrpc.ReadError().execute(request)
                emit(systemMessage)
            } catch (e: Exception) {
                logger.error(e) { "Failed to get error" }
            }
            delay(100L)
        }
    }.shareIn(scope, started = SharingStarted.Lazily, replay = 1)
}
