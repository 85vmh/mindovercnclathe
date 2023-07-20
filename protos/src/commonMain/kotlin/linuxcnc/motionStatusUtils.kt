package linuxcnc

import ro.dragossusi.proto.linuxcnc.status.*

fun MotionStatus.isHomed(count: Int): Boolean {
    // todo fix this as it checks too many items
    joint_status.take(count).forEach { if (it.homed.not()) return false }
    return true
}

val MotionStatus.isXHomed
    get() = joint_status[0].homed
val MotionStatus.isZHomed
    get() = joint_status[1].homed

val MotionStatus.isXHoming
    get() = joint_status[0].homing
val MotionStatus.isZHoming
    get() = joint_status[1].homing

val MotionStatus.isSpindleOn
    get() =
        spindle_status.first().direction == SpindleDirection.REVERSE ||
                spindle_status.first().direction == SpindleDirection.FORWARD

val MotionStatus.jogVelocity
    get() = trajectory_status?.max_velocity

val MotionStatus.isMinSoftLimitOnX
    get() = joint_status[0].min_soft_limit_exceeded
val MotionStatus.isMaxSoftLimitOnX
    get() = joint_status[0].max_soft_limit_exceeded
val MotionStatus.isMinSoftLimitOnZ
    get() = joint_status[1].min_soft_limit_exceeded
val MotionStatus.isMaxSoftLimitOnZ
    get() = joint_status[1].max_soft_limit_exceeded

val TaskStatus.isInterpreterIdle
    get() = interpreterState == InterpreterState.Idle

val TaskStatus.isDiameterMode
    get() = activeCodes?.g_codes?.contains(7.0f)

val MotionStatus.g53Position: Position?
    get() = trajectory_status?.actual_position
