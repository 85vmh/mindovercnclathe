package com.mindovercnc.linuxcnc

import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.dispatchers.createScope
import com.mindovercnc.linuxcnc.model.CncStatus
import com.mindovercnc.linuxcnc.parsing.CncStatusFactory
import com.mindovercnc.repository.CncStatusRepository
import kotlinx.coroutines.flow.*

class CncStatusRepositoryImpl
constructor(ioDispatcher: IoDispatcher, private val cncStatusFactory: CncStatusFactory) :
  CncStatusRepository {

  private val statusReader: StatusReader = StatusReader()
  private val statusFlow =
    statusReader
      .refresh(100L)
      .filterNotNull()
      .map { cncStatusFactory.parse(it) }
      .shareIn(ioDispatcher.createScope(), SharingStarted.Eagerly, replay = 1)

  override fun cncStatusFlow(): Flow<CncStatus> {
    return statusFlow
  }
}
