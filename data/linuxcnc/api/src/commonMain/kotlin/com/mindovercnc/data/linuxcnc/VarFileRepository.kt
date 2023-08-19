package com.mindovercnc.data.linuxcnc

import com.mindovercnc.data.linuxcnc.model.ParametersState
import kotlinx.coroutines.flow.Flow

interface VarFileRepository {
    fun getParametersState(): Flow<ParametersState>
}