package com.mindovercnc.linuxcnc.domain.gcode

import com.mindovercnc.model.Point3D

interface GcodeCommandParseScope {
    var lastPoint: Point3D?
}
