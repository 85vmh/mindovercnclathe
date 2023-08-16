package com.mindovercnc.linuxcnc.domain.model

sealed class SimpleCycleStatus {
    data class FacingCycle(val xEnd: Double, val zEnd: Double, val doc: Double) : SimpleCycleStatus()
    data class TurningCycle(val xEnd: Double, val zEnd: Double, val doc: Double, val taperAngle: Double, val filletRadius: Double) : SimpleCycleStatus()
    data class BoringCycle(val xEnd: Double, val zEnd: Double, val doc: Double, val taperAngle: Double, val filletRadius: Double) : SimpleCycleStatus()
}
