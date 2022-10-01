package com.mindovercnc.repository

import com.mindovercnc.model.MessageBundle
import com.mindovercnc.model.UiMessage
import kotlinx.coroutines.flow.Flow

interface MessagesRepository {
    fun messagesFlow(): Flow<MessageBundle>

    fun clearEmcMessages()

    fun pushMessage(uiMessage: UiMessage)

    fun popMessage(uiMessage: UiMessage)
}

fun MessagesRepository.handleMessage(isNeeded: Boolean, uiMessage: UiMessage) {
    if (isNeeded) {
        pushMessage(uiMessage)
    } else {
        popMessage(uiMessage)
    }
}