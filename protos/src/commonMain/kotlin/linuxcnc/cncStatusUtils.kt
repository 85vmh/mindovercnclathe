package linuxcnc

import ro.dragossusi.proto.linuxcnc.CncStatus
import ro.dragossusi.proto.linuxcnc.status.Position
import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

val CncStatus.g53Position: Position?
    get() = motion_status?.g53Position

@Deprecated("Use motion status", level = DeprecationLevel.ERROR)
val CncStatus.dtg
    get() = motion_status?.trajectory_status?.dtg

fun CncStatus.getDisplayablePosition(): Position {
    val machinePosition = g53Position!!
    val g5xOffset = task_status!!.g5xOffset!!
    val toolOffset = task_status.toolOffset!!
    val rotationXY = task_status.rotationXY
    val g92Offset = task_status.g92Offset!!

    var position = Position(
        x = machinePosition.x - g5xOffset.x - toolOffset.x,
        y = machinePosition.y - g5xOffset.y - toolOffset.y,
        z = machinePosition.z - g5xOffset.z - toolOffset.z,
        a = machinePosition.a - g5xOffset.a - toolOffset.a,
        b = machinePosition.b - g5xOffset.b - toolOffset.b,
        c = machinePosition.c - g5xOffset.c - toolOffset.c,
        u = machinePosition.u - g5xOffset.u - toolOffset.u,
        v = machinePosition.v - g5xOffset.v - toolOffset.v,
        w = machinePosition.w - g5xOffset.w - toolOffset.w,
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
        x = position.x - g92Offset.x,
        y = position.y - g92Offset.y,
        z = position.z - g92Offset.z,
        a = position.a - g92Offset.a,
        b = position.b - g92Offset.b,
        c = position.c - g92Offset.c,
        u = position.u - g92Offset.u,
        v = position.v - g92Offset.v,
        w = position.w - g92Offset.w,
    )
}

internal fun toRadians(degrees: Double) = degrees * PI / 180.0