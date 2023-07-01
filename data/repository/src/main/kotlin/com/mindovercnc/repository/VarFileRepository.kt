package com.mindovercnc.repository

import com.mindovercnc.model.ParametersState
import kotlinx.coroutines.flow.Flow

interface VarFileRepository {
    fun getParametersState(): Flow<ParametersState>
}