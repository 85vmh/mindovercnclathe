package com.mindovercnc.repository

import kotlinx.coroutines.flow.Flow
import ro.dragossusi.proto.linuxcnc.CncStatus

interface CncStatusRepository {
  fun cncStatusFlow(): Flow<CncStatus>
}
