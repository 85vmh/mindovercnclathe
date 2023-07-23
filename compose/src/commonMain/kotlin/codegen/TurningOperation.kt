package codegen

import extensions.stripZeros

class TurningOperation(
    private val profileGeometry: ProfileGeometry,
    private val turningStrategies: List<TurningStrategy>,
    private val startingXPosition: Double,
    private val startingZPosition: Double,
) : Operation {

    override fun getStartComment(): List<String> {
        return mutableListOf<String>().apply {
            add("(---BEGIN---Turning operation-----------)")
        }
    }

    override fun getEndComment(): List<String> {
        return mutableListOf<String>().apply {
            add("(----END----Turning operation-----------)")
        }
    }

    override fun getOperationCode(): List<String> {
        return mutableListOf<String>().apply {
            add("(Profile of the cut)")
            addAll(subroutineLines)
            var previousToolNumber = 0
            var previousFeedRate = 0.0
            var previousCssSpeed = 0
            var previousMaxSpindleSpeed = 0
            turningStrategies.forEach {
                when (it) {
                    is TurningStrategy.Roughing -> {
                        add("\r")
                        add("(Roughing)")
                        add("G40 M6 T${it.toolNumber} G43")
                        previousToolNumber = it.toolNumber
                        add("G96 S${it.cssSpeed} D${it.maxSpindleSpeed}")
                        previousCssSpeed = it.cssSpeed
                        previousMaxSpindleSpeed = it.maxSpindleSpeed
                        add("F${it.feedRate.stripZeros()}")
                        previousFeedRate = it.feedRate
                        add(it.direction.code + it.cutType.prefix + commonParams + it.strategyParams)
                    }
                    is TurningStrategy.Finishing -> {
                        add("\r")
                        add("(Finishing)")
                        if (it.toolNumber != previousToolNumber) {
                            add("G40 M6 T${it.toolNumber} G43")
                            previousToolNumber = it.toolNumber
                        }
                        if(it.cssSpeed != previousCssSpeed || it.maxSpindleSpeed != previousMaxSpindleSpeed){
                            add("G96 S${it.cssSpeed} D${it.maxSpindleSpeed}")
                            previousCssSpeed = it.cssSpeed
                            previousMaxSpindleSpeed = it.maxSpindleSpeed
                        }
                        if (it.feedRate != previousFeedRate) {
                            add("F${it.feedRate.stripZeros()}")
                            previousFeedRate = it.feedRate
                        }
                        add("G70" + commonParams + it.strategyParams)
                    }
                }
            }
            add("G0 X$startingXPosition Z$startingZPosition")
        }
    }

    enum class CutType(val prefix: String) {
        Straight(".1"),
        Pocket(".2")
    }

    enum class TraverseDirection(val code: String) {
        ZAxis("G71"), XAxis("G72")
    }

    sealed class TurningStrategy() {
        abstract val strategyParams: String

        data class Roughing(
            val toolNumber: Int,
            val cssSpeed: Int,
            val maxSpindleSpeed: Int,
            val remainingDistance: Double,
            val cuttingIncrement: Double,
            val retractDistance: Double,
            val cutType: CutType,
            val direction: TraverseDirection,
            val feedRate: Double
        ) : TurningStrategy() {
            override val strategyParams: String
                get() = " D${remainingDistance.stripZeros()}" +
                        " I${cuttingIncrement.stripZeros()}" +
                        " R${retractDistance.stripZeros()}"
        }

        data class Finishing(
            val toolNumber: Int,
            val cssSpeed: Int,
            val maxSpindleSpeed: Int,
            val startingDistance: Double,
            val endingDistance: Double,
            val numberOfPasses: Int,
            val pathBlendingTolerance: Double = 0.01,
            val feedRate: Double
        ) : TurningStrategy() {
            override val strategyParams: String
                get() = " D${startingDistance.stripZeros()}" +
                        " E${endingDistance.stripZeros()}" +
                        " P$numberOfPasses"
        }
    }

    private val commonParams: String
        get() = " Q${profileGeometry.subroutineNumber}" +
                " X${startingXPosition.stripZeros()}" +
                " Z${startingZPosition.stripZeros()}"

    private val subroutineLines = mutableListOf<String>().apply {
        add("O${profileGeometry.subroutineNumber} SUB")
        profileGeometry.getProfile().forEach {
            add("\t$it")
        }
        add("O${profileGeometry.subroutineNumber} ENDSUB")
    }
}