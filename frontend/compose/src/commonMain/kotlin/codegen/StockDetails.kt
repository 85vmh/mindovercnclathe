package codegen

import screen.uimodel.WorkpieceMaterial

data class StockDetails(
    val outsideDiameter: Double,
    val insideDiameter: Double? = null,
    val material: WorkpieceMaterial,
)
