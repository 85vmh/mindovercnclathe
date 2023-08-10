package com.mindovercnc.linuxcnc.gcode

import okio.Path

interface GCodeRepository {
    fun parseFile(file: Path): List<GcodeCommand>
}