package com.mindovercnc.linuxcnc.domain.gcode

import actor.PathElement
import com.mindovercnc.linuxcnc.gcode.model.GCodeCommand

interface GcodeCommandParser {
    fun GcodeCommandParseScope.parse(command: GCodeCommand): PathElement?
}
