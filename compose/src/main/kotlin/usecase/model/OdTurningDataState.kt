package usecase.model

import androidx.compose.runtime.mutableStateOf
import screen.uimodel.CutDirection
import screen.uimodel.CuttingStrategy
import screen.uimodel.Wcs
import screen.uimodel.WorkpieceMaterial

class OdTurningDataState(
    wcs: Wcs,
    cutDirection: CutDirection,
    material: WorkpieceMaterial,
    cuttingStrategy: CuttingStrategy,
    val roughingParameters: CuttingParametersState? = null,
    val finishingParameters: CuttingParametersState? = null,
    toolClearance: Double,
    spindleMaxSpeed: Int,
    xInitial: Double,
    xFinal: Double,
    zStart: Double,
    zEnd: Double,
    fillet: Double
) {
    val wcs = mutableStateOf(wcs)
    val cutDirection = mutableStateOf(cutDirection)
    val material = mutableStateOf(material)
    val cuttingStrategy = mutableStateOf(cuttingStrategy)
    val toolClearance = mutableStateOf(toolClearance)
    val spindleMaxSpeed = mutableStateOf(spindleMaxSpeed)
    val xInitial = mutableStateOf(xInitial)
    val xFinal = mutableStateOf(xFinal)
    val zStart = mutableStateOf(zStart)
    val zEnd = mutableStateOf(zEnd)
    val fillet = mutableStateOf(fillet)
}