package com.mindovercnc.linuxcnc.screen.status.root

import com.arkivanov.decompose.ComponentContext
import com.mindovercnc.linuxcnc.domain.MessagesUseCase
import com.mindovercnc.linuxcnc.screen.BaseScreenModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import org.kodein.di.DI
import org.kodein.di.instance

class StatusRootScreenModel(di: DI, componentContext: ComponentContext) :
    BaseScreenModel<StatusRootState>(StatusRootState(), componentContext), StatusRootComponent {

    private val messagesUseCase by di.instance<MessagesUseCase>()

    init {
        messagesUseCase
            .getAllMessages()
            .onEach { messagesList -> mutableState.update { it.copy(messages = messagesList) } }
            .launchIn(coroutineScope)
    }
}
