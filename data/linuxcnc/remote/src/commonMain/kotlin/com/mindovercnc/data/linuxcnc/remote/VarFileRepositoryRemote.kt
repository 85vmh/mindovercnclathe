package com.mindovercnc.data.linuxcnc.remote

import com.mindovercnc.data.linuxcnc.VarFileRepository
import com.mindovercnc.data.linuxcnc.model.ParametersState
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
