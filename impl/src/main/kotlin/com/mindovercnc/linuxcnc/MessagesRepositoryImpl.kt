package com.mindovercnc.linuxcnc

import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.dispatchers.createScope
import com.mindovercnc.linuxcnc.model.SystemMessage
import com.mindovercnc.model.MessageBundle
import com.mindovercnc.model.UiMessage
import com.mindovercnc.repository.MessagesRepository
import java.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class MessagesRepositoryImpl(ioDispatcher: IoDispatcher) : MessagesRepository {
  private val errorReader: ErrorReader = ErrorReader()
  private val emcMessages = MutableStateFlow(emptyList<SystemMessage>())
  private val uiMessages = MutableStateFlow(mapOf<UiMessage, Date>())

  private val scope = ioDispatcher.createScope()

  init {
    errorReader
      .refresh(10L)
      .filterNotNull()
      .onEach { emcMessages.update { list -> list.plus(it) } }
      .flowOn(Dispatchers.IO)
      .launchIn(scope)
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
