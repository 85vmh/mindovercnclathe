package com.mindovercnc.repository

import com.mindovercnc.linuxcnc.model.CncStatus
import kotlinx.coroutines.flow.Flow

interface CncStatusRepository {
    fun cncStatusFlow() : Flow<CncStatus>
}