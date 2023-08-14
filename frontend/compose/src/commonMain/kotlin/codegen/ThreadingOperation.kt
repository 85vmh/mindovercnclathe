package codegen

import com.mindovercnc.linuxcnc.format.stripZeros
import com.mindovercnc.linuxcnc.format.toFixedDigitsString
import kotlin.math.sqrt

class ThreadingOperation(
    private val toolNumber: Int,
    private val spindleSpeed: Int,
    private val startPoint: Point,
    private val threadPitch: Double,
    private val finalZPosition: Double,
    private val threadPeakOffset: Double,
    private val initialCutDepth: Double,
    private val fullThreadDepth: Double = calculateDepth(threadPitch, threadPeakOffset),
    private val threadStarts: Int = 1,
    private val springPasses: Int? = null,
    private val depthDegression: DepthDegression? = null,
    private val infeedAngle: InfeedAngle? = null,
    private val taper: Taper? = null
) : Operation {

    override fun getStartComment(): List<String> {
        return buildList { add("(---BEGIN---Threading operation-----------)") }
    }

    override fun getEndComment(): List<String> {
        return buildList { add("(----END----Threading operation-----------)") }
    }

    override fun getOperationCode(): List<String> {
        val lines = mutableListOf<String>()
        lines.add("G95")
        lines.add("G97 S$spindleSpeed")
        lines.add("M6 T$toolNumber G43")

        val leadOffset = threadPitch / threadStarts
        for (lead in 1..threadStarts) {
            val zOffset = (leadOffset * (lead - 1))
            lines.add("(Cutting thread lead [$lead] of [$threadStarts], zOffset is: $zOffset)")
            lines.add(
                "G0 X${startPoint.x.toFixedDigitsString()} Z${(startPoint.z + zOffset).toFixedDigitsString()}"
            )
            lines.add(buildThreadingCode(finalZPosition + zOffset))
        }
        return lines
    }

    sealed class Taper(val code: Int, val length: Double) {
        data class AtStart(val start: Double) : Taper(1, start)
        data class AtEnd(val end: Double) : Taper(2, end)
        data class AtBoth(val both: Double) : Taper(3, both)
    }

    sealed class TaperAngle(val code: Int, val angle: Double) {
        data class AtStart(val startAngle: Double) : TaperAngle(1, startAngle)
        data class AtEnd(val endAngle: Double) : TaperAngle(2, endAngle)
        data class AtBoth(val bothAngle: Double) : TaperAngle(3, bothAngle)
    }

    sealed class DepthDegression(val value: Double) {
        object ConstantDepth : DepthDegression(1.0)
        object ConstantArea : DepthDegression(2.0)
        data class Custom(val customValue: Double) : DepthDegression(customValue)
    }

    enum class InfeedAngle(val value: Double) {
        Angle290(29.0),
        Angle295(29.5),
        Angle300(30.0)
    }

    private fun buildThreadingCode(finalZPosition: Double): String {
        val builder = StringBuilder()
        builder.append(
            "G76 " +
                "P${threadPitch.stripZeros()} " +
                "Z${finalZPosition.stripZeros()} " +
                "I${threadPeakOffset.stripZeros()} " +
                "J${initialCutDepth.stripZeros()} " +
                "K${fullThreadDepth.stripZeros()} "
        )
        if (depthDegression != null) {
            builder.append("R${depthDegression.value.stripZeros()} ")
        }
        if (infeedAngle != null) {
            builder.append("Q${infeedAngle.value.stripZeros()} ")
        }
        if (springPasses != null) {
            builder.append("H$springPasses ")
        }
        if (taper != null) {
            builder.append("L${taper.code} E${taper.length.stripZeros()}")
        }
        return builder.toString()
    }

    companion object {
        fun calculateDepth(threadPitch: Double, threadPeak: Double): Double {
            val triangleHeight = sqrt(3.0) / 2 * threadPitch
            // threadPeak is negative for external threads, positive for internal threads.
            val isExternalThread = threadPeak < 0
            return when {
                isExternalThread -> triangleHeight - (triangleHeight / 4)
                else -> triangleHeight - (triangleHeight / 8)
            }
        }
    }
}
