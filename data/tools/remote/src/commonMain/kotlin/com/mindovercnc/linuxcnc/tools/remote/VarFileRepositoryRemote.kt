package com.mindovercnc.linuxcnc.tools.remote

import com.mindovercnc.linuxcnc.tools.VarFileRepository
import com.mindovercnc.model.ParametersState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import mu.KotlinLogging

/** Implementation for [VarFileRepository]. */
class VarFileRepositoryRemote : VarFileRepository {

    init {
        LOG.warn { "Remote implementation is missing" }
    }

    override fun getParametersState(): Flow<ParametersState> {
        /* no-op */
        return emptyFlow()
    }

    companion object {
        private val LOG = KotlinLogging.logger("VarFileRepository")
    }
}
