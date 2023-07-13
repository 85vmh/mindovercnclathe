package com.mindovercnc.linuxcnc.legacy

import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.dispatchers.createScope
import com.mindovercnc.linuxcnc.StatusReader
import com.mindovercnc.linuxcnc.parsing.CncStatusFactory
import com.mindovercnc.repository.CncStatusRepository
import kotlinx.coroutines.flow.*

class CncStatusRepositoryLegacy
constructor(ioDispatcher: IoDispatcher, private val cncStatusFactory: CncStatusFactory) :
  CncStatusRepository {

  private val statusReader: StatusReader = StatusReader()

  override val cncStatusFlow =
    statusReader
      .refresh(100L)
      .filterNotNull()
      .map { cncStatusFactory.parse(it) }
      .shareIn(ioDispatcher.createScope(), SharingStarted.Eagerly, replay = 1)
}
