package com.mindovercnc.linuxcnc

import com.mindovercnc.model.ParametersState
import com.mindovercnc.repository.VarFileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import mu.KotlinLogging

/** Implementation for [VarFileRepository]. */
class VarFileRepositoryImpl : VarFileRepository {

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
