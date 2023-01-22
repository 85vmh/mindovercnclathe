package com.mindovercnc.repository

import kotlinx.coroutines.flow.Flow
import ro.dragossusi.proto.linuxcnc.status.SystemMessage

/** Repository for [SystemMessage]. */
interface SystemMessageRepository {

  /** A flow of [SystemMessage]. */
  val systemMessageFlow: Flow<SystemMessage>
}
