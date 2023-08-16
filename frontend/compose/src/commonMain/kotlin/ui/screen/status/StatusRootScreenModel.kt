package ui.screen.status

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.mindovercnc.linuxcnc.domain.MessagesUseCase
import com.mindovercnc.linuxcnc.domain.model.Message
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class StatusRootScreenModel(
    messagesUseCase: MessagesUseCase
) : StateScreenModel<StatusRootScreenModel.State>(State()) {

    data class State(
        val messages: List<Message> = emptyList()
    )

    init {
        messagesUseCase.getAllMessages()
            .onEach { messagesList ->
                mutableState.update {
                    it.copy(messages = messagesList)
                }
            }.launchIn(coroutineScope)
    }
}