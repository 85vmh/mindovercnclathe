package com.mindovercnc.linuxcnc

import com.mindovercnc.model.CncStateMessage
import com.mindovercnc.repository.CncMessagesRepository
import com.mindovercnc.repository.EmcMessagesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import mu.KotlinLogging

/** Implementation for [EmcMessagesRepository]. */
class CncMessagesRepositoryImpl(
    private val clock: Clock,
) : CncMessagesRepository {

    private val uiMessages = MutableStateFlow(emptyMap<CncStateMessage, Instant>())
    override val messagesFlow = uiMessages.asStateFlow()

    private val uiMessageMutex = Mutex()

    override suspend fun pushMessage(uiMessage: CncStateMessage) {
        updateUiMessages { current ->
            val pair = uiMessage to clock.now()
            LOG.debug { "Pushing message $pair" }
            current.plus(pair)
        }
    }

    override suspend fun popMessage(uiMessage: CncStateMessage) {
        updateUiMessages { current ->
            LOG.debug { "Popping message $uiMessage" }
            current.minus(uiMessage)
        }
    }

    private suspend fun updateUiMessages(
        block: (Map<CncStateMessage, Instant>) -> Map<CncStateMessage, Instant>
    ) {
        uiMessageMutex.withLock { uiMessages.update(block) }
    }

    companion object {
        private val LOG = KotlinLogging.logger("MessagesRepository")
    }
}
