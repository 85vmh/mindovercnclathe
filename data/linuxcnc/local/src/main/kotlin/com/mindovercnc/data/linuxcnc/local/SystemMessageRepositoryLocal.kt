package com.mindovercnc.data.linuxcnc.local

import com.mindovercnc.data.linuxcnc.SystemMessageRepository
import com.mindovercnc.dispatchers.NewSingleThreadDispatcher
import com.mindovercnc.dispatchers.createScope
import com.mindovercnc.linuxcnc.ErrorReader
import kotlinx.coroutines.flow.*
import mu.KotlinLogging
import ro.dragossusi.proto.linuxcnc.status.MessageType
import ro.dragossusi.proto.linuxcnc.status.SystemMessage
import com.mindovercnc.linuxcnc.model.SystemMessage.MessageType as KtlCncMessageType

// TODO split into legacy and remote
/** Implementation for [SystemMessageRepository]. */
class SystemMessageRepositoryLocal(
    errorReader: ErrorReader,
    newSingleThreadDispatcher: NewSingleThreadDispatcher
) : SystemMessageRepository {

    private val scope = newSingleThreadDispatcher.createScope()

    override val systemMessageFlow: Flow<SystemMessage> =
        errorReader.refresh(10L)
            .filterNotNull()
            .map { message ->
                SystemMessage(
                    message = message.message,
                    type = message.type.toProtoType(),
                    timestamp_millis = message.time.time,
                )
            }
            .onEach { message ->
                LOG.info { "New message received: $message" }
            }
            .shareIn(scope, started = SharingStarted.Lazily, replay = 1)

    private fun KtlCncMessageType.toProtoType()= when(this) {
        KtlCncMessageType.NMLError -> MessageType.NML_Error
        KtlCncMessageType.NMLText -> MessageType.NML_Text
        KtlCncMessageType.NMLDisplay -> MessageType.NML_Display
        KtlCncMessageType.CommandLog -> MessageType.NML_Text
        KtlCncMessageType.OperatorError -> MessageType.NML_Error
        KtlCncMessageType.OperatorText -> MessageType.NML_Text
        KtlCncMessageType.OperatorDisplay -> MessageType.NML_Display
    }

    companion object {
        private val LOG = KotlinLogging.logger("SystemMessageRepositoryImpl")
    }
}
