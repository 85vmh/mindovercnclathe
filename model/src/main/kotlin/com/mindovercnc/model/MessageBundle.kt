package com.mindovercnc.model

import java.util.*
import ro.dragossusi.proto.linuxcnc.status.SystemMessage

data class MessageBundle(
  val emcMessages: List<SystemMessage>,
  val uiMessages: Map<UiMessage, Date>
)
