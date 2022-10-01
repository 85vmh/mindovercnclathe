package usecase.model

data class VirtualLimits(
   val xMinus: Double,
   val xPlus: Double,
   val zMinus: Double,
   val zPlus: Double,
   val xMinusActive: Boolean,
   val xPlusActive: Boolean,
   val zMinusActive: Boolean,
   val zPlusActive: Boolean,
   val zPlusIsToolRelated: Boolean
)