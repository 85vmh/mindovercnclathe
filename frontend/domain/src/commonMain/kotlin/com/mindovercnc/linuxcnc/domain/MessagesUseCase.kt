package com.mindovercnc.linuxcnc.domain

import com.mindovercnc.linuxcnc.domain.model.Message
import com.mindovercnc.model.CncStateMessage
import com.mindovercnc.repository.CncMessagesRepository
import com.mindovercnc.repository.EmcMessagesRepository
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import mu.KotlinLogging
import ro.dragossusi.proto.linuxcnc.status.MessageType

class MessagesUseCase(
    private val emcMessagesRepository: EmcMessagesRepository,
    private val cncMessagesRepository: CncMessagesRepository,
) {
    fun getAllMessages() =
        combine(
            emcMessagesRepository.messagesFlow,
            cncMessagesRepository.messagesFlow,
        ) { emcMessages, uiMessages ->
            buildList {
                emcMessages.forEach { emcMsg ->
                    this.add(Message(emcMsg.message, emcMsg.type.toMessageLevel()))
                }
                uiMessages.forEach { uiMsg ->
                    this.add(Message(uiMsg.key.name, uiMsg.key.level.toMessageLevel()))
                }
            }
        }
            .onEach { LOG.debug { "Message list is: $it" } }

    private fun CncStateMessage.Level.toMessageLevel() =
        when (this) {
            CncStateMessage.Level.Error -> Message.Level.ERROR
            CncStateMessage.Level.Warning -> Message.Level.WARNING
            CncStateMessage.Level.Info -> Message.Level.INFO
        }

    private fun MessageType.toMessageLevel() =
        when (this) {
            MessageType.NML_Error,
            MessageType.Operator_Error -> {
                Message.Level.ERROR
            }

            else -> {
                Message.Level.INFO
            }
        }

    companion object {
        private val LOG = KotlinLogging.logger("MessagesUseCase")
    }
}
