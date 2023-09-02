package com.mindovercnc.linuxcnc.screen.status.root

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.mindovercnc.linuxcnc.domain.MessagesUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import org.kodein.di.DI
import org.kodein.di.instance

class StatusRootScreenModel(di: DI) :
    StateScreenModel<StatusRootState>(StatusRootState()), StatusRootComponent {

    private val messagesUseCase by di.instance<MessagesUseCase>()

    init {
        messagesUseCase
            .getAllMessages()
            .onEach { messagesList -> mutableState.update { it.copy(messages = messagesList) } }
            .launchIn(coroutineScope)
    }
}
