package com.mindovercnc.repository

import kotlinx.coroutines.flow.Flow
import ro.dragossusi.proto.linuxcnc.status.IoStatus

interface IoStatusRepository {
  val ioStatusFlow: Flow<IoStatus>
}
