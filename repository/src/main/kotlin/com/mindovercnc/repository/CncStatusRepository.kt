package com.mindovercnc.repository

import kotlinx.coroutines.flow.Flow
import ro.dragossusi.proto.linuxcnc.CncStatus

/** Repository for [CncStatus]. */
interface CncStatusRepository {
  /** Returns a flow of [CncStatus]. */
  val cncStatusFlow: Flow<CncStatus>
}
