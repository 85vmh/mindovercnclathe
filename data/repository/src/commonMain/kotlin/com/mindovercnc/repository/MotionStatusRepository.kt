package com.mindovercnc.repository

import kotlinx.coroutines.flow.Flow
import ro.dragossusi.proto.linuxcnc.status.MotionStatus

/** Repository for [MotionStatus]. */
interface MotionStatusRepository {
  /** A flow of [MotionStatus]. */
  val motionStatusFlow: Flow<MotionStatus>
}
