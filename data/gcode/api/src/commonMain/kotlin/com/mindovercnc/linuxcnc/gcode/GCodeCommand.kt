package com.mindovercnc.linuxcnc.gcode

data class GCodeCommand(
    val id: Int,
    val name: String,
    val arguments: String,
    val rawLine: String
)