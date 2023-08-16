package com.mindovercnc.model.codegen.operation

import com.mindovercnc.model.WorkpieceMaterial

data class StockDetails(
    val outsideDiameter: Double,
    val insideDiameter: Double? = null,
    val material: WorkpieceMaterial,
)
