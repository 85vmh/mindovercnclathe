package com.mindovercnc.linuxcnc.screen.status.root

import com.mindovercnc.linuxcnc.domain.model.Message

data class StatusRootState(
    val messages: List<Message> = emptyList(),
)
