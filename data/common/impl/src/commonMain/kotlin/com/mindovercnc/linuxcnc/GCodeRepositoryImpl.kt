package com.mindovercnc.linuxcnc

import com.mindovercnc.model.GcodeCommand
import com.mindovercnc.repository.GCodeRepository
import mu.KotlinLogging
import okio.Path

/** Implementation for [GCodeRepository]. */
class GCodeRepositoryImpl : GCodeRepository {

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
