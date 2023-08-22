package com.mindovercnc.linuxcnc.screen.tools.root.data

sealed interface Cutter {
    val cutterNo: Int

    data class OdTurning(
        override val cutterNo: Int,
    ) : Cutter

    data class IdTurning(
        override val cutterNo: Int,
        val minDiameter: Double,
        val maxDepth: Double,
    ) : Cutter

    data class Drill(
        override val cutterNo: Int,
        val diameter: Double,
        val maxDepth: Double,
    ) : Cutter

    data class Threading(
        override val cutterNo: Int,
    ) : Cutter
}
