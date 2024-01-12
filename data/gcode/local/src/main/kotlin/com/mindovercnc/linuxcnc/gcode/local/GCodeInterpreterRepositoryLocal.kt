package com.mindovercnc.linuxcnc.gcode.local

import com.mindovercnc.linuxcnc.gcode.GCodeInterpreterRepository
import com.mindovercnc.linuxcnc.gcode.model.GCodeCommand
import okio.Path
import okio.Path.Companion.toPath

val LinuxCncHome = System.getenv("LINUXCNC_HOME").toPath()

/** Implementation for [GCodeInterpreterRepository]. */
class GCodeInterpreterRepositoryLocal(
    private val gcodeReader: GcodeReader,
) : GCodeInterpreterRepository {

    override suspend fun parseFile(file: Path): List<GCodeCommand> {
        return gcodeReader.readFile(file)
    }
}
