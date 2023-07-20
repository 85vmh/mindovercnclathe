package com.mindovercnc.linuxcnc

import com.mindovercnc.repository.CncStatusRepository
import com.mindovercnc.repository.IoStatusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ro.dragossusi.proto.linuxcnc.status.IoStatus

/** Implementation for [IoStatusRepository]. */
class IoStatusRepositoryImpl(private val cncStatusRepository: CncStatusRepository) :
  IoStatusRepository {
  override val ioStatusFlow: Flow<IoStatus>
    get() = cncStatusRepository.cncStatusFlow.map { it.io_status!! }
}
