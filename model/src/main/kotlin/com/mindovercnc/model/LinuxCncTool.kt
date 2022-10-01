package com.mindovercnc.model

/**
 * This represents a 1:1 mapping with a line from tool.tbl file.
 * This file is the one used by linuxcnc to get awareness about the tool.
 */
data class LinuxCncTool(
    val toolNo: Int,
    val pocket: Int = toolNo,
    val orientation: TipOrientation,
    val xOffset: Double,
    val zOffset: Double,
    val frontAngle: Double,
    val backAngle: Double,
    val tipRadius: Double,
    val comment: String
) {

    fun toFormattedString(): String {
        return "T$toolNo P$pocket X$xOffset Z$zOffset D$tipRadius I$frontAngle J$backAngle Q${orientation.orient} ;$comment"
    }

    class Builder {
        var toolNo: Int = 0
        var orientation: TipOrientation = TipOrientation.Position2
        var xOffset = 0.0
        var zOffset = 0.0
        var frontAngle = 0.0
        var backAngle = 0.0
        var tipRadius = 0.0
        var comment = ""

        fun build() = LinuxCncTool(
            toolNo = toolNo,
            orientation = orientation,
            xOffset = xOffset,
            zOffset = zOffset,
            frontAngle = frontAngle,
            backAngle = backAngle,
            tipRadius = tipRadius,
            comment = comment
        )
    }
}
