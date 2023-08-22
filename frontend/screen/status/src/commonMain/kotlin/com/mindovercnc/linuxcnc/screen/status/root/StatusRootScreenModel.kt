package com.mindovercnc.linuxcnc.screen.status.root

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.mindovercnc.linuxcnc.domain.MessagesUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

internal class StatusRootScreenModel(messagesUseCase: MessagesUseCase) :
    StateScreenModel<StatusRootState>(StatusRootState()), StatusRootComponent {

    init {
        messagesUseCase
            .getAllMessages()
            .onEach { messagesList -> mutableState.update { it.copy(messages = messagesList) } }
            .launchIn(coroutineScope)
    }
}
