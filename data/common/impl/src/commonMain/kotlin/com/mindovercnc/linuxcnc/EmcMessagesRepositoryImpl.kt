package com.mindovercnc.linuxcnc

import com.mindovercnc.data.linuxcnc.SystemMessageRepository
import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.dispatchers.createScope
import com.mindovercnc.model.CncStateMessage
import com.mindovercnc.repository.EmcMessagesRepository
import kotlinx.coroutines.flow.*
import kotlinx.datetime.Instant
import mu.KotlinLogging
import ro.dragossusi.proto.linuxcnc.status.SystemMessage

/** Implementation for [EmcMessagesRepository]. */
class EmcMessagesRepositoryImpl(
    private val systemMessageRepository: SystemMessageRepository,
    ioDispatcher: IoDispatcher,
) : EmcMessagesRepository {

    private val _messagesFlow = MutableStateFlow(emptyList<SystemMessage>())

    private val scope = ioDispatcher.createScope()

    override val messagesFlow: Flow<List<SystemMessage>> = _messagesFlow.onStart { subscribe() }

    private var subscribed = false

    private fun subscribe() {
        if (subscribed) {
            LOG.debug { "Trying to subscribe, but already subscribed" }
            return
        }
        LOG.debug { "Subscribing to system messages" }
        systemMessageRepository.systemMessageFlow
            .onEach { message -> _messagesFlow.update { list -> list.plus(message) } }
            .launchIn(scope)
        subscribed = true
    }

    override fun clearEmcMessages() {
        _messagesFlow.value = emptyList()
    }

    companion object {
        private val LOG = KotlinLogging.logger("EmcMessagesRepository")
    }
}
