package ui.screen.tools.root

data class Tool(
    val holder: Holder,
    val cutter: Cutter? = null,
    val xOffset: Double,
    val zOffset: Double,
)

data class Holder(
    val number: Int,
    val type: Type
) {
    enum class Type {
        Generic,
        Parting,
        Drilling
    }
}

interface Cutter {
    val cutterNo: Int
}

data class OdTurning(
    override val cutterNo: Int
) : Cutter

data class IdTurning(
    override val cutterNo: Int,
    val minDiameter: Double,
    val maxDepth: Double
) : Cutter

data class Drill(
    override val cutterNo: Int,
    val diameter: Double,
    val maxDepth: Double
) : Cutter

data class Threading(
    override val cutterNo: Int,
) : Cutter