package usecase

import com.mindovercnc.linuxcnc.model.SystemMessage
import com.mindovercnc.model.UiMessage
import com.mindovercnc.repository.MessagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import usecase.model.Message

class MessagesUseCase(private val messagesRepository: MessagesRepository) {
  fun getAllMessages(): Flow<List<Message>> {
    return messagesRepository
      .messagesFlow()
      .map {
        buildList {
          it.emcMessages.forEach { emcMsg ->
            when (emcMsg.type) {
              SystemMessage.MessageType.NMLError,
              SystemMessage.MessageType.OperatorError -> {
                this.add(Message(emcMsg.message, Message.Level.ERROR))
              }
              else -> {
                this.add(Message(emcMsg.message, Message.Level.INFO))
              }
            }
          }
          it.uiMessages.forEach { uiMsg ->
            val level =
              when (uiMsg.key.level) {
                UiMessage.Level.Error -> Message.Level.ERROR
                UiMessage.Level.Warning -> Message.Level.WARNING
                UiMessage.Level.Info -> Message.Level.INFO
              }
            this.add(Message(uiMsg.key.name, level))
          }
        }
      }
      .onEach { println("Message list is: $it") }
  }
}
