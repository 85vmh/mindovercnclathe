package com.mindovercnc.repository

import com.mindovercnc.model.CncStateMessage
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.Instant

/** Repository for messages. */
interface CncMessagesRepository {
    suspend fun pushMessage(uiMessage: CncStateMessage)

    suspend fun popMessage(uiMessage: CncStateMessage)
    val messagesFlow: StateFlow<Map<CncStateMessage, Instant>>
}

suspend fun CncMessagesRepository.handleMessage(
    isNeeded: Boolean,
    uiMessage: CncStateMessage,
) {
    if (isNeeded) {
        pushMessage(uiMessage)
    } else {
        popMessage(uiMessage)
    }
}
