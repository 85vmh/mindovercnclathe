package ro.dragossusi.proto.linuxcnc

import ro.dragossusi.proto.linuxcnc.status.*

fun MotionStatus.isHomed(count: Int): Boolean {
  // todo fix this as it checks too many items
  jointStatusList.take(count).forEach { if (it.homed.not()) return false }
  return true
}

val MotionStatus.isXHomed
  get() = jointStatusList[0].homed
val MotionStatus.isZHomed
  get() = jointStatusList[1].homed

val MotionStatus.isXHoming
  get() = jointStatusList[0].homing
val MotionStatus.isZHoming
  get() = jointStatusList[1].homing

val MotionStatus.isSpindleOn
  get() =
    spindleStatusList[0].direction == SpindleDirection.REVERSE ||
      spindleStatusList[0].direction == SpindleDirection.FORWARD

val MotionStatus.jogVelocity
  get() = trajectoryStatus.maxVelocity

val MotionStatus.isMinSoftLimitOnX
  get() = jointStatusList[0].minSoftLimitExceeded
val MotionStatus.isMaxSoftLimitOnX
  get() = jointStatusList[0].maxSoftLimitExceeded
val MotionStatus.isMinSoftLimitOnZ
  get() = jointStatusList[1].minSoftLimitExceeded
val MotionStatus.isMaxSoftLimitOnZ
  get() = jointStatusList[1].maxSoftLimitExceeded

val TaskStatus.isInterpreterIdle
  get() = interpreterState == InterpreterState.Idle

val TaskStatus.isDiameterMode
  get() = activeCodes.gCodesList.contains(7.0f)

val MotionStatus.g53Position: Position
  get() = trajectoryStatus.actualPosition
