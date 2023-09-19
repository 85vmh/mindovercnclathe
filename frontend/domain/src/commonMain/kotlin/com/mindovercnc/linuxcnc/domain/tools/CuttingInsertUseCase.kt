package com.mindovercnc.linuxcnc.domain.tools

import com.mindovercnc.linuxcnc.tools.CuttingInsertsRepository
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert

class CuttingInsertUseCase
constructor(private val cuttingInsertsRepository: CuttingInsertsRepository) {

    suspend fun createCuttingInsert(cuttingInsert: CuttingInsert) =
        cuttingInsertsRepository.insert(cuttingInsert)

    suspend fun updateCuttingInsert(cuttingInsert: CuttingInsert) =
        cuttingInsertsRepository.update(cuttingInsert)

    suspend fun getCuttingInserts(): List<CuttingInsert> = cuttingInsertsRepository.findAll()

    suspend fun deleteCuttingInsert(insert: CuttingInsert) = cuttingInsertsRepository.delete(insert)
}
