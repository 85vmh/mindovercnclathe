package com.mindovercnc.linuxcnc.gcode.model

data class GCodeCommand(
    val id: Int,
    val name: String,
    val arguments: String,
    val rawLine: String
)