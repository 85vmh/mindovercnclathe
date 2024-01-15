package com.mindovercnc.linuxcnc.domain.gcode.impl

import actor.PathElement
import com.mindovercnc.linuxcnc.domain.gcode.GcodeCommandParseScope
import com.mindovercnc.linuxcnc.domain.gcode.GcodeCommandParser
import com.mindovercnc.linuxcnc.gcode.model.GCodeCommand

/** [GcodeCommandParser] that handles comment commands. */
object CommentGcodeCommandParser : GcodeCommandParser {
    override fun GcodeCommandParseScope.parse(command: GCodeCommand): PathElement? {
        /* no-op */
        return null
    }
}
