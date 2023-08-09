package usecase.model

import androidx.compose.runtime.mutableStateOf
import codegen.ThreadingOperation

abstract class CycleParametersState(
    xEnd: Double,
    zEnd: Double,
    doc: Double
) {
    val xEnd = mutableStateOf(xEnd)
    val zEnd = mutableStateOf(zEnd)
    val doc = mutableStateOf(doc)
}

class DummyParameterState() : CycleParametersState(0.0, 0.0, 0.0)

class FacingParameterState(
    xEnd: Double,
    zEnd: Double,
    doc: Double
) : CycleParametersState(xEnd, zEnd, doc)

class TurningParameterState(
    xEnd: Double,
    zEnd: Double,
    doc: Double,
    turnAngle: Double = 0.0,
    filletRadius: Double = 0.0
) : CycleParametersState(xEnd, zEnd, doc) {
    val turnAngle = mutableStateOf(turnAngle)
    val filletRadius = mutableStateOf(filletRadius)
}

class BoringParameterState(
    xEnd: Double,
    zEnd: Double,
    doc: Double,
    turnAngle: Double = 0.0,
    filletRadius: Double = 0.0
) : CycleParametersState(xEnd, zEnd, doc) {
    val turnAngle = mutableStateOf(turnAngle)
    val filletRadius = mutableStateOf(filletRadius)
}

class ThreadingParameterState(
    xEnd: Double,
    zEnd: Double,
    firstPassDepth: Double,
    taperAngle: ThreadingOperation.TaperAngle,
    depthDegression: ThreadingOperation.DepthDegression,
    infeedAngle: ThreadingOperation.InfeedAngle,
    majorDiameter: Double,
    threadPitch: Double = 1.0,
    springPasses: Int = 1
) : CycleParametersState(xEnd, zEnd, firstPassDepth) {
    val majorDiameter = mutableStateOf(majorDiameter)
    val threadPitch = mutableStateOf(threadPitch)
    val springPasses = mutableStateOf(springPasses)
    val taperAngle = mutableStateOf(taperAngle)
    val depthDegression = mutableStateOf(depthDegression)
    val infeedAngle = mutableStateOf(infeedAngle)
}
