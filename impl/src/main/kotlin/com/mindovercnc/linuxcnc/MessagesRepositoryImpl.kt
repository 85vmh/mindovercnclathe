package com.mindovercnc.linuxcnc

import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.dispatchers.createScope
import com.mindovercnc.model.MessageBundle
import com.mindovercnc.model.UiMessage
import com.mindovercnc.repository.MessagesRepository
import com.mindovercnc.repository.SystemMessageRepository
import java.util.*
import kotlinx.coroutines.flow.*
import ro.dragossusi.proto.linuxcnc.status.SystemMessage

/** Implementation for [MessagesRepository]. */
class MessagesRepositoryImpl(
  systemMessageRepository: SystemMessageRepository,
  ioDispatcher: IoDispatcher,
) : MessagesRepository {

  @Deprecated("Will be replaced by grpc.") private val errorReader: ErrorReader = ErrorReader()

  private val emcMessages = MutableStateFlow(emptyList<SystemMessage>())
  private val uiMessages = MutableStateFlow(mapOf<UiMessage, Date>())

  private val scope = ioDispatcher.createScope()

  @Deprecated("Will be replaced by messageFlow", ReplaceWith("messageFlow"))
  private val jniMessageFlow = errorReader.refresh(10L).filterNotNull()

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
    uiMessages.update { it.plus((uiMessage to Date())) }
  }

  override fun popMessage(uiMessage: UiMessage) {
    uiMessages.update { it.minus(uiMessage) }
  }
}
