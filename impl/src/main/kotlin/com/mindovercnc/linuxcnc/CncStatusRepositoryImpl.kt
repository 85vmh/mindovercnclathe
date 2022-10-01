package com.mindovercnc.linuxcnc

import com.mindovercnc.repository.CncStatusRepository
import com.mindovercnc.linuxcnc.model.CncStatus
import com.mindovercnc.linuxcnc.parsing.CncStatusFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

class CncStatusRepositoryImpl constructor(
    scope: CoroutineScope,
    private val cncStatusFactory: CncStatusFactory
) : CncStatusRepository {
    private val statusReader: StatusReader = StatusReader()
    private val statusFlow = statusReader.refresh(100L)
        .filterNotNull()
        .map { cncStatusFactory.parse(it) }
        .shareIn(scope, SharingStarted.Eagerly, replay = 1)


    override fun cncStatusFlow(): Flow<CncStatus> {
        return statusFlow
    }
}