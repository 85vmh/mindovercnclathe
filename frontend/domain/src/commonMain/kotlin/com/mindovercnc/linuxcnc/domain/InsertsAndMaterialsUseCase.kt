package com.mindovercnc.linuxcnc.domain

import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.dispatchers.createScope
import com.mindovercnc.linuxcnc.tools.CuttingInsertsRepository
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert
import kotlinx.coroutines.flow.*

class InsertsAndMaterialsUseCase(
  ioDispatcher: IoDispatcher,
  private val cuttingInsertsRepository: CuttingInsertsRepository,
) {

  private val scope = ioDispatcher.createScope()
  fun createCuttingInsert(cuttingInsert: CuttingInsert) {}

  fun updateCuttingInsert(cuttingInsert: CuttingInsert) {}

  fun getCuttingInserts(): Flow<List<CuttingInsert>> {
    return flowOf(cuttingInsertsRepository.findAll())
  }
}
