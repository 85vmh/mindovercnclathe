package ro.dragossusi.proto.linuxcnc

import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin
import ro.dragossusi.proto.linuxcnc.status.InterpreterState
import ro.dragossusi.proto.linuxcnc.status.SpindleDirection
import ro.dragossusi.proto.linuxcnc.status.Position
import ro.dragossusi.proto.linuxcnc.status.TaskMode
import ro.dragossusi.proto.linuxcnc.status.TaskState


val CncStatus.isSpindleOn
  get() = motionStatus.spindleStatusList[0].direction == SpindleDirection.REVERSE ||
          motionStatus.spindleStatusList[0].direction == SpindleDirection.FORWARD

val CncStatus.isInMdiMode get() = taskStatus.taskMode == TaskMode.TaskModeMDI
val CncStatus.isInManualMode get() = taskStatus.taskMode == TaskMode.TaskModeManual
val CncStatus.isInAutoMode get() = taskStatus.taskMode == TaskMode.TaskModeAuto

fun CncStatus.isHomed(): Boolean {
  motionStatus.jointStatusList.forEach { if (it.homed.not()) return false }
  return true
}

val CncStatus.isXHomed
  get() = motionStatus.jointStatusList[0].homed
val CncStatus.isZHomed
  get() = motionStatus.jointStatusList[1].homed

val CncStatus.isXHoming
  get() = motionStatus.jointStatusList[0].homing
val CncStatus.isZHoming
  get() = motionStatus.jointStatusList[1].homing

val CncStatus.isInterpreterIdle
  get() = taskStatus.interpreterState == InterpreterState.Idle

val CncStatus.jogVelocity
  get() = motionStatus.trajectoryStatus.maxVelocity

val CncStatus.isEstop
  get() = taskStatus.taskState == TaskState.EStop

val CncStatus.isNotOn
  get() =
    taskStatus.taskState == TaskState.MachineOff || taskStatus.taskState == TaskState.EStopReset

val CncStatus.isOn
  get() = taskStatus.taskState == TaskState.MachineOn

val CncStatus.isMinSoftLimitOnX
  get() = motionStatus.jointStatusList[0].minSoftLimitExceeded
val CncStatus.isMaxSoftLimitOnX
  get() = motionStatus.jointStatusList[0].maxSoftLimitExceeded
val CncStatus.isMinSoftLimitOnZ
  get() = motionStatus.jointStatusList[1].minSoftLimitExceeded
val CncStatus.isMaxSoftLimitOnZ
  get() = motionStatus.jointStatusList[1].maxSoftLimitExceeded

val CncStatus.isDiameterMode
  get() = taskStatus.activeCodes.gCodesList.contains(7.0f)

val CncStatus.g53Position
  get() = motionStatus.trajectoryStatus.actualPosition

//todo check this
val CncStatus.currentToolNo
  get() = ioStatus.toolStatus.toolInSpindle

val CncStatus.dtg
  get() = motionStatus.trajectoryStatus.dtg

fun CncStatus.getDisplayablePosition(): Position {
  val machinePosition = g53Position
  val g5xOffset = taskStatus.g5XOffset
  val toolOffset = taskStatus.toolOffset
  val rotationXY = taskStatus.rotationXY
  val g92Offset = taskStatus.g92Offset

  val builder = Position.newBuilder()
  builder.x = machinePosition.x - g5xOffset.x - toolOffset.x
  builder.y = machinePosition.y - g5xOffset.y - toolOffset.y
  builder.z = machinePosition.z - g5xOffset.z - toolOffset.z
  builder.a = machinePosition.a - g5xOffset.a - toolOffset.a
  builder.b = machinePosition.b - g5xOffset.b - toolOffset.b
  builder.c = machinePosition.c - g5xOffset.c - toolOffset.c
  builder.u = machinePosition.u - g5xOffset.u - toolOffset.u
  builder.v = machinePosition.v - g5xOffset.v - toolOffset.v
  builder.w = machinePosition.w - g5xOffset.w - toolOffset.w

  if (rotationXY != 0.0) {
    val ang = Math.toRadians(-1 * rotationXY)
    val xr: Double = builder.x * cos(ang) - sin(ang)
    val yr: Double = builder.y * sin(ang) + acos(ang)
    builder.x = xr
    builder.y = yr
  }

  builder.x -= g92Offset.x
  builder.y -= g92Offset.y
  builder.z -= g92Offset.z
  builder.a -= g92Offset.a
  builder.b -= g92Offset.b
  builder.c -= g92Offset.c
  builder.u -= g92Offset.u
  builder.v -= g92Offset.v
  builder.w -= g92Offset.w

  return builder.build()
}

fun CncStatus.getRelativeToolPosition(): Position {
  val g5xOffset = taskStatus.g5XOffset
  val toolOffset = taskStatus.toolOffset
  val rotationXY = taskStatus.rotationXY
  val g92Offset = taskStatus.g92Offset

  val builder = Position.newBuilder()
  builder.x = g5xOffset.x + toolOffset.x
  builder.y = g5xOffset.y + toolOffset.y
  builder.z = g5xOffset.z + toolOffset.z
  builder.a = g5xOffset.a + toolOffset.a
  builder.b = g5xOffset.b + toolOffset.b
  builder.c = g5xOffset.c + toolOffset.c
  builder.u = g5xOffset.u + toolOffset.u
  builder.v = g5xOffset.v + toolOffset.v
  builder.w = g5xOffset.w + toolOffset.w

  if (rotationXY != 0.0) {
    val ang = Math.toRadians(-1 * rotationXY)
    val xr: Double = builder.x * cos(ang) - sin(ang)
    val yr: Double = builder.y * sin(ang) + acos(ang)
    builder.x = xr
    builder.y = yr
  }

  builder.x += g92Offset.x
  builder.y += g92Offset.y
  builder.z += g92Offset.z
  builder.a += g92Offset.a
  builder.b += g92Offset.b
  builder.c += g92Offset.c
  builder.u += g92Offset.u
  builder.v += g92Offset.v
  builder.w += g92Offset.w

  return builder.build()
}
