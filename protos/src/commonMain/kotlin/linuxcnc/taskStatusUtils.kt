package linuxcnc

import ro.dragossusi.proto.linuxcnc.status.Position
import ro.dragossusi.proto.linuxcnc.status.TaskState
import ro.dragossusi.proto.linuxcnc.status.TaskStatus
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

private const val SETTING_FEED_RATE_INDEX = 1
private const val SETTING_SPINDLE_SPEED_INDEX = 2

/** The set feed rate, obtained from active settings */
val TaskStatus.setFeedRate: Double?
    get() = activeSettings[SETTING_FEED_RATE_INDEX]

/** The set spindle speed, obtained from active settings */
val TaskStatus.setSpindleSpeed: Double?
    get() = activeSettings[SETTING_SPINDLE_SPEED_INDEX]

val TaskStatus.isEstop
    get() = taskState == TaskState.EStop

val TaskStatus.isNotOn
    get() = taskState == TaskState.MachineOff || taskState == TaskState.EStopReset

val TaskStatus.isOn
    get() = taskState == TaskState.MachineOn

fun TaskStatus.getRelativeToolPosition(): Position {
    val g5xOffset = g5xOffset!!
    val toolOffset = toolOffset!!
    val rotationXY = rotationXY
    val g92Offset = g92Offset!!

    var position = Position(
        x = g5xOffset.x + toolOffset.x,
        y = g5xOffset.y + toolOffset.y,
        z = g5xOffset.z + toolOffset.z,
        a = g5xOffset.a + toolOffset.a,
        b = g5xOffset.b + toolOffset.b,
        c = g5xOffset.c + toolOffset.c,
        u = g5xOffset.u + toolOffset.u,
        v = g5xOffset.v + toolOffset.v,
        w = g5xOffset.w + toolOffset.w,
    )
    if (rotationXY != 0.0) {
        val ang = toRadians(-1 * rotationXY)
        val xr: Double = position.x * cos(ang) - sin(ang)
        val yr: Double = position.y * sin(ang) + acos(ang)
        position = position.copy(
            x = xr,
            y = yr
        )
    }

    return position.copy(
        x = position.x + g92Offset.x,
        y = position.y + g92Offset.y,
        z = position.z + g92Offset.z,
        a = position.a + g92Offset.a,
        b = position.b + g92Offset.b,
        c = position.c + g92Offset.c,
        u = position.u + g92Offset.u,
        v = position.v + g92Offset.v,
        w = position.w + g92Offset.w,
    )
}