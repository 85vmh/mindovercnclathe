package com.mindovercnc.linuxcnc

import com.mindovercnc.repository.CncStatusRepository
import com.mindovercnc.repository.MotionStatusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ro.dragossusi.proto.linuxcnc.status.MotionStatus

/** Implementation for [MotionStatusRepository]. */
class MotionStatusRepositoryImpl(private val cncStatusRepository: CncStatusRepository) :
  MotionStatusRepository {
  override val motionStatusFlow: Flow<MotionStatus>
    get() = cncStatusRepository.cncStatusFlow.map { it.motion_status!! }
}
