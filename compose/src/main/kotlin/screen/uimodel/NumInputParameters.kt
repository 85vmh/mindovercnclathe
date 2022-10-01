package screen.uimodel

data class NumInputParameters(
    val valueDescription: String = "Input Name",
    val unit: String? = null,
    val minValue: Double = Double.MIN_VALUE,
    val maxValue: Double = Double.MAX_VALUE,
    val allowsNegativeValues: Boolean = false,
    val maxDecimalPlaces: Int = 0,
    val initialValue: Double = 0.0
)