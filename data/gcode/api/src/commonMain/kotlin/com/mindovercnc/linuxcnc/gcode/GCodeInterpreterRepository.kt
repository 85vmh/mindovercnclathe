package com.mindovercnc.linuxcnc.gcode

import com.mindovercnc.linuxcnc.gcode.model.GCodeCommand
import okio.Path

interface GCodeInterpreterRepository {
    suspend fun parseFile(file: Path): List<GCodeCommand>
}