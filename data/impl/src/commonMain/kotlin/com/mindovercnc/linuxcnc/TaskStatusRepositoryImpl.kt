package com.mindovercnc.linuxcnc

import com.mindovercnc.repository.CncStatusRepository
import com.mindovercnc.repository.TaskStatusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ro.dragossusi.proto.linuxcnc.status.TaskStatus

/** Implementation for [TaskStatusRepository]. */
class TaskStatusRepositoryImpl(private val cncStatusRepository: CncStatusRepository) :
  TaskStatusRepository {
  override val taskStatusFlow: Flow<TaskStatus>
    get() = cncStatusRepository.cncStatusFlow.map { it.task_status!! }
}
