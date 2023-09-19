package com.mindovercnc.linuxcnc.tools

import com.mindovercnc.linuxcnc.tools.model.CuttingInsert

interface CuttingInsertsRepository {
    suspend fun insert(cuttingInsert: CuttingInsert)

    suspend fun update(cuttingInsert: CuttingInsert)

    suspend fun findAll(): List<CuttingInsert>

    suspend fun delete(cuttingInsert: CuttingInsert)
}
