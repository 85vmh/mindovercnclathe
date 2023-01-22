package com.mindovercnc.repository

import kotlinx.coroutines.flow.Flow
import ro.dragossusi.proto.linuxcnc.status.TaskStatus

/** Repository for [TaskStatus]. */
interface TaskStatusRepository {
  /** A flow of [TaskStatus]. */
  val taskStatusFlow: Flow<TaskStatus>
}
