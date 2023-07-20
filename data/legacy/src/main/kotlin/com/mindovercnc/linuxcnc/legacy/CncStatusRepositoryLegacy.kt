package com.mindovercnc.linuxcnc.legacy

import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.dispatchers.createScope
import com.mindovercnc.linuxcnc.StatusReader
import com.mindovercnc.linuxcnc.parsing.CncStatusFactory
import com.mindovercnc.repository.CncStatusRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn

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