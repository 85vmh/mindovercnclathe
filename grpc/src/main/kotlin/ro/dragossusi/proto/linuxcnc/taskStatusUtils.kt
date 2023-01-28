package ro.dragossusi.proto.linuxcnc

import ro.dragossusi.proto.linuxcnc.status.Position
import ro.dragossusi.proto.linuxcnc.status.TaskState
import ro.dragossusi.proto.linuxcnc.status.TaskStatus
import ro.dragossusi.proto.linuxcnc.status.position
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

private const val SETTING_FEED_RATE_INDEX = 1
private const val SETTING_SPINDLE_SPEED_INDEX = 2

/** The set feed rate, obtained from active settings */
val TaskStatus.setFeedRate: Double?
  get() = activeSettingsList[SETTING_FEED_RATE_INDEX]

/** The set spindle speed, obtained from active settings */
val TaskStatus.setSpindleSpeed: Double?
  get() = activeSettingsList[SETTING_SPINDLE_SPEED_INDEX]

val TaskStatus.isEstop
  get() = taskState == TaskState.EStop

val TaskStatus.isNotOn
  get() = taskState == TaskState.MachineOff || taskState == TaskState.EStopReset

val TaskStatus.isOn
  get() = taskState == TaskState.MachineOn

fun TaskStatus.getRelativeToolPosition(): Position {
  val g5xOffset = g5XOffset
  val toolOffset = toolOffset
  val rotationXY = rotationXY
  val g92Offset = g92Offset

  return position {
    x = g5xOffset.x + toolOffset.x
    y = g5xOffset.y + toolOffset.y
    z = g5xOffset.z + toolOffset.z
    a = g5xOffset.a + toolOffset.a
    b = g5xOffset.b + toolOffset.b
    c = g5xOffset.c + toolOffset.c
    u = g5xOffset.u + toolOffset.u
    v = g5xOffset.v + toolOffset.v
    w = g5xOffset.w + toolOffset.w

    if (rotationXY != 0.0) {
      val ang = Math.toRadians(-1 * rotationXY)
      val xr: Double = x * cos(ang) - sin(ang)
      val yr: Double = y * sin(ang) + acos(ang)
      x = xr
      y = yr
    }

    x += g92Offset.x
    y += g92Offset.y
    z += g92Offset.z
    a += g92Offset.a
    b += g92Offset.b
    c += g92Offset.c
    u += g92Offset.u
    v += g92Offset.v
    w += g92Offset.w
  }
}