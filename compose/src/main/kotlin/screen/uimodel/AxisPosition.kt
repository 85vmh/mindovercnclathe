package screen.uimodel

data class AxisPosition(
    val axis: Axis,
    val primaryValue: Double,
    val secondaryValue: Double? = null,
    val units: Units
) {
    enum class Axis {
        X, Z
    }

    enum class Units(val displayDigits: Int) {
        MM(3), IN(4), CM(2)
    }
}
