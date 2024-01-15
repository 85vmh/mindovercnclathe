package com.mindovercnc.linuxcnc.domain.gcode.impl

import com.mindovercnc.linuxcnc.domain.gcode.GcodeCommandParseScope
import com.mindovercnc.model.Point3D

/** Implementation for [GcodeCommandParseScope]. */
class GcodeCommandParseScopeImpl : GcodeCommandParseScope {
    override var lastPoint: Point3D? = null
}
