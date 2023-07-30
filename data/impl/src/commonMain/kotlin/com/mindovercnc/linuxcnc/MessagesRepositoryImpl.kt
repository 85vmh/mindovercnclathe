package com.mindovercnc.linuxcnc

import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.dispatchers.createScope
import com.mindovercnc.model.MessageBundle
import com.mindovercnc.model.UiMessage
import com.mindovercnc.repository.MessagesRepository
import com.mindovercnc.repository.SystemMessageRepository
import kotlinx.coroutines.flow.*
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import ro.dragossusi.proto.linuxcnc.status.SystemMessage

/** Implementation for [MessagesRepository]. */
class MessagesRepositoryImpl(
  private val clock: Clock,
  systemMessageRepository: SystemMessageRepository,
  ioDispatcher: IoDispatcher,
) : MessagesRepository {

  private val emcMessages = MutableStateFlow(emptyList<SystemMessage>())
  private val uiMessages = MutableStateFlow(mapOf<UiMessage, Instant>())

  private val scope = ioDispatcher.createScope()

  private val messageFlow = systemMessageRepository.systemMessageFlow

  init {
    messageFlow.onEach { emcMessages.update { list -> list.plus(it) } }.launchIn(scope)
  }

  override fun messagesFlow(): Flow<MessageBundle> {
    return combine(emcMessages, uiMessages) { emcFlow, uiFlow -> MessageBundle(emcFlow, uiFlow) }
  }

  override fun clearEmcMessages() {
    emcMessages.value = emptyList()
  }

  override fun pushMessage(uiMessage: UiMessage) {
    uiMessages.update { it.plus((uiMessage to clock.now())) }
  }

  override fun popMessage(uiMessage: UiMessage) {
    uiMessages.update { it.minus(uiMessage) }
  }
}
