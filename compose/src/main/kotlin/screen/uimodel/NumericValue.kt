package screen.uimodel

class NumericValue(
    val value: Double = 0.0,
    val minValue: Double = Double.MIN_VALUE,
    val maxValue: Double = Double.MAX_VALUE,
    val allowsNegativeValues: Boolean = false,
    val maxDecimalPlaces: Int = 0
) {
}