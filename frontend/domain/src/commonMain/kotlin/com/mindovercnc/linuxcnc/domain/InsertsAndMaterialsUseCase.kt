package com.mindovercnc.linuxcnc.domain

import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.dispatchers.createScope
import com.mindovercnc.linuxcnc.tools.CuttingInsertsRepository
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert

class InsertsAndMaterialsUseCase(
    ioDispatcher: IoDispatcher,
    private val cuttingInsertsRepository: CuttingInsertsRepository,
) {

    private val scope = ioDispatcher.createScope()
    suspend fun createCuttingInsert(cuttingInsert: CuttingInsert) {}

    suspend fun updateCuttingInsert(cuttingInsert: CuttingInsert) {}

    suspend fun getCuttingInserts(): List<CuttingInsert> {
        return cuttingInsertsRepository.findAll()
    }
}
