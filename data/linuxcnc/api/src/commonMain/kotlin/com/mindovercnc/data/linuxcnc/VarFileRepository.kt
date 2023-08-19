package com.mindovercnc.data.linuxcnc

import com.mindovercnc.model.ParametersState
import kotlinx.coroutines.flow.Flow

interface VarFileRepository {
    fun getParametersState(): Flow<ParametersState>
}