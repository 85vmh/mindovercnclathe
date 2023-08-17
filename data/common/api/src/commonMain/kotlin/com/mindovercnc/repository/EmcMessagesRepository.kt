package com.mindovercnc.repository

import kotlinx.coroutines.flow.Flow
import ro.dragossusi.proto.linuxcnc.status.SystemMessage

/** Repository for messages. */
interface EmcMessagesRepository {
    val messagesFlow: Flow<List<SystemMessage>>

    fun clearEmcMessages()
}
