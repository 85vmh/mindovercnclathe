package extensions

import androidx.compose.foundation.gestures.*
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.roundToInt

fun Double.toPercent(): Int = (this * 100).roundToInt()

/** Sets the displayable digits to max digits, which will be shown even if they are zero. */
fun Double.toFixedDigitsString(maxDigits: Int = 3): String {
    return BigDecimal(this).setScale(maxDigits, RoundingMode.HALF_EVEN).toString()
}

fun Double.toFixedDigits(maxDigits: Int = 3): Double {
    return BigDecimal(this).setScale(maxDigits, RoundingMode.HALF_EVEN).toDouble()
}

/** Strips the decimal points digits that are zero. */
fun Double.stripZeros(maxDigits: Int = 3): String {
    return BigDecimal(this)
        .setScale(maxDigits, RoundingMode.HALF_EVEN)
        .stripTrailingZeros()
        .toPlainString()
}
