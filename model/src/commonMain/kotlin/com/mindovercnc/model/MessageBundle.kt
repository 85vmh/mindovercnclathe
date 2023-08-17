package com.mindovercnc.model

import kotlinx.datetime.Instant
import ro.dragossusi.proto.linuxcnc.status.SystemMessage

data class MessageBundle(
  val emcMessages: List<SystemMessage>,
  val uiMessages: Map<CncStateMessage, Instant>
)
