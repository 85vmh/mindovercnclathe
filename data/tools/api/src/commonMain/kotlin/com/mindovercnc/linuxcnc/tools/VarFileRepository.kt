package com.mindovercnc.linuxcnc.tools

import com.mindovercnc.model.ParametersState
import kotlinx.coroutines.flow.Flow

interface VarFileRepository {
    fun getParametersState(): Flow<ParametersState>
}