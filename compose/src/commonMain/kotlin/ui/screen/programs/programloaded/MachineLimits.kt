package ui.screen.programs.programloaded

import com.mindovercnc.model.WcsLimits
import org.jetbrains.skia.Point

/**
 * This represents the machine limits as per the .ini file
 * When linuxcnc starts up, the values from the ini files are loaded into the hal component.
 * When virtual limits are applied, the machine limits will reflect the new machine limits.
 */
data class MachineLimits(
    val xMin: Double = 0.0,
    val xMax: Double = 0.0,
    val zMin: Double = 0.0,
    val zMax: Double = 0.0
) {
    fun toWcsLimits(wcsPosition: Point) = WcsLimits(
        xMin = xMin - wcsPosition.x,
        xMax = xMax - wcsPosition.x,
        zMin = zMin - wcsPosition.y,
        zMax = zMax - wcsPosition.y,
    )
}