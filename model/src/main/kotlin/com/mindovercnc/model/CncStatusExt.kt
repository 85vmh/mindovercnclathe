package com.mindovercnc.model

import com.mindovercnc.linuxcnc.model.CncStatus
import com.mindovercnc.linuxcnc.model.InterpreterState
import com.mindovercnc.linuxcnc.model.Position
import com.mindovercnc.linuxcnc.model.TaskState
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

fun CncStatus.isHomed(): Boolean {
    motionStatus.jointsStatus.forEach {
        if (it.isHomed.not()) return false
    }
    return true
}

val CncStatus.isXHomed get() = motionStatus.jointsStatus[0].isHomed
val CncStatus.isZHomed get() = motionStatus.jointsStatus[1].isHomed

val CncStatus.isXHoming get() = motionStatus.jointsStatus[0].isHoming
val CncStatus.isZHoming get() = motionStatus.jointsStatus[1].isHoming

val CncStatus.isInterpreterIdle get() = taskStatus.interpreterState == InterpreterState.Idle

val CncStatus.jogVelocity get() = motionStatus.trajectoryStatus.maxVelocity

val CncStatus.isEstop get() = taskStatus.taskState == TaskState.EStop

val CncStatus.isNotOn get() = taskStatus.taskState == TaskState.MachineOff || taskStatus.taskState == TaskState.EStopReset

val CncStatus.isOn get() = taskStatus.taskState == TaskState.MachineOn

val CncStatus.isMinSoftLimitOnX get() = motionStatus.jointsStatus[0].minSoftLimitExceeded
val CncStatus.isMaxSoftLimitOnX get() = motionStatus.jointsStatus[0].maxSoftLimitExceeded
val CncStatus.isMinSoftLimitOnZ get() = motionStatus.jointsStatus[1].minSoftLimitExceeded
val CncStatus.isMaxSoftLimitOnZ get() = motionStatus.jointsStatus[1].maxSoftLimitExceeded

val CncStatus.isDiameterMode get() = taskStatus.activeCodes.gCodes.contains(7.0f)

val CncStatus.g53Position get() = motionStatus.trajectoryStatus.currentActualPosition

val CncStatus.currentToolNo get() = ioStatus.toolStatus.currentLoadedTool

val CncStatus.dtg get() = motionStatus.trajectoryStatus.distance2Go

fun CncStatus.getDisplayablePosition(): Position {
    val machinePosition = g53Position
    val g5xOffset = taskStatus.g5xOffset
    val toolOffset = taskStatus.toolOffset
    val rotationXY = taskStatus.rotationXY
    val g92Offset = taskStatus.g92Offset

    val builder = Position.Builder()
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
    val g5xOffset = taskStatus.g5xOffset
    val toolOffset = taskStatus.toolOffset
    val rotationXY = taskStatus.rotationXY
    val g92Offset = taskStatus.g92Offset

    val builder = Position.Builder()
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
