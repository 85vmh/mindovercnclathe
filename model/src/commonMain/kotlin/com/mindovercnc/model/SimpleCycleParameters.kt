package com.mindovercnc.model

sealed class SimpleCycleParameters(
    val simpleCycle: SimpleCycle
) {

    data class TurningParameters(
        val xEnd: Double,
        val zEnd: Double,
        val doc: Double,
        val taperAngle: Double = 0.0,
        val filletRadius: Double = 0.0
    ) : SimpleCycleParameters(SimpleCycle.Turning)

    data class BoringParameters(
        val xEnd: Double,
        val zEnd: Double,
        val doc: Double,
        val taperAngle: Double = 0.0,
        val filletRadius: Double = 0.0
    ) : SimpleCycleParameters(SimpleCycle.Boring)

    data class FacingParameters(
        val xEnd: Double,
        val zEnd: Double,
        val doc: Double
    ) : SimpleCycleParameters(SimpleCycle.Facing)

    data class DrillingParameters(
        val zEnd: Double,
        val retract: Double,
        val increment: Double,
        val rpm: Double,
        val feed: Double,
    ) : SimpleCycleParameters(SimpleCycle.Drilling)

    data class KeySlotParameters(
        val zEnd: Double,
        val xEnd: Double,
        val doc: Double,
        val feedPerMinute: Double
    ) : SimpleCycleParameters(SimpleCycle.KeySlot)

    data class ThreadingParameters(
        val isExternal: Boolean = true,
        val threadType: ThreadType = ThreadType.Metric,
        val pitch: Double,
        val starts: Int = 1,
        val zEnd: Double,
        val majorDiameter: Double,
        val minorDiameter: Double,
        val firstPassDepth: Double,
        val finalDepth: Double,
        val springPasses: Int = 1,
    ) : SimpleCycleParameters(SimpleCycle.Threading) {
        enum class ThreadType {
            Metric, Imperial
        }
    }

    object DummyParameters : SimpleCycleParameters(SimpleCycle.KeySlot)
}