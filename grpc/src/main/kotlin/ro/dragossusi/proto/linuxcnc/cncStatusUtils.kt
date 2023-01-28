package ro.dragossusi.proto.linuxcnc

import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin
import ro.dragossusi.proto.linuxcnc.status.*

val CncStatus.g53Position: Position
  get() = motionStatus.g53Position

@Deprecated("Use motion status", level = DeprecationLevel.ERROR)
val CncStatus.dtg
  get() = motionStatus.trajectoryStatus.dtg

fun CncStatus.getDisplayablePosition(): Position {
  val machinePosition = g53Position
  val g5xOffset = taskStatus.g5XOffset
  val toolOffset = taskStatus.toolOffset
  val rotationXY = taskStatus.rotationXY
  val g92Offset = taskStatus.g92Offset

  return position {
    x = machinePosition.x - g5xOffset.x - toolOffset.x
    y = machinePosition.y - g5xOffset.y - toolOffset.y
    z = machinePosition.z - g5xOffset.z - toolOffset.z
    a = machinePosition.a - g5xOffset.a - toolOffset.a
    b = machinePosition.b - g5xOffset.b - toolOffset.b
    c = machinePosition.c - g5xOffset.c - toolOffset.c
    u = machinePosition.u - g5xOffset.u - toolOffset.u
    v = machinePosition.v - g5xOffset.v - toolOffset.v
    w = machinePosition.w - g5xOffset.w - toolOffset.w

    if (rotationXY != 0.0) {
      val ang = Math.toRadians(-1 * rotationXY)
      val xr: Double = x * cos(ang) - sin(ang)
      val yr: Double = y * sin(ang) + acos(ang)
      x = xr
      y = yr
    }

    x -= g92Offset.x
    y -= g92Offset.y
    z -= g92Offset.z
    a -= g92Offset.a
    b -= g92Offset.b
    c -= g92Offset.c
    u -= g92Offset.u
    v -= g92Offset.v
    w -= g92Offset.w
  }
}
