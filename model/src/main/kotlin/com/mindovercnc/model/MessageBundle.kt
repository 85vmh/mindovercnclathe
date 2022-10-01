package com.mindovercnc.model

import com.mindovercnc.linuxcnc.model.SystemMessage
import java.util.*

data class MessageBundle(
    val emcMessages: List<SystemMessage>,
    val uiMessages: Map<UiMessage, Date>
)
