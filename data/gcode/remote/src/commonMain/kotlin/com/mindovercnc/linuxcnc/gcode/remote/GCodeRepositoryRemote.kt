package com.mindovercnc.linuxcnc.gcode.remote

import com.mindovercnc.linuxcnc.gcode.GCodeRepository
import com.mindovercnc.linuxcnc.gcode.GcodeCommand
import mu.KotlinLogging
import okio.Path

/** Implementation for [GCodeRepository]. */
class GCodeRepositoryRemote : GCodeRepository {

    init {
        LOG.warn { "Remote implementation is missing" }
    }

    override fun parseFile(file: Path): List<GcodeCommand> {
        return emptyList()
    }

    companion object {
        private val LOG = KotlinLogging.logger("GCodeRepository")
    }

}
