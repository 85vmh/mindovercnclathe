package com.mindovercnc.linuxcnc.gcode

import okio.Path

interface GCodeInterpreterRepository {
    fun parseFile(file: Path): List<GCodeCommand>
}