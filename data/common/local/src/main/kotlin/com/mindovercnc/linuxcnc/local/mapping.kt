package com.mindovercnc.linuxcnc.local

import com.mindovercnc.database.entity.CuttingInsertEntity
import com.mindovercnc.database.entity.WorkpieceMaterialEntity
import com.mindovercnc.model.CuttingInsert
import com.mindovercnc.model.WorkpieceMaterial


fun CuttingInsertEntity.toCuttingInsert(): CuttingInsert {
    return CuttingInsert(
        id = id.value, madeOf = madeOf, code = code, tipRadius = tipRadius, tipAngle = tipAngle, size = size
    )
}

fun WorkpieceMaterialEntity.toWorkpieceMaterial(): WorkpieceMaterial {
    return WorkpieceMaterial(
        id = id.value,
        name = name,
    )
}