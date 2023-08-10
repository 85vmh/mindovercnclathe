package extensions

import com.ionspin.kotlin.bignum.decimal.DecimalMode
import com.ionspin.kotlin.bignum.decimal.RoundingMode
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import kotlin.math.PI
import kotlin.math.roundToInt
import kotlin.text.format

fun Double.toPercent(): Int = (this * 100).roundToInt()

/** Sets the displayable digits to max digits, which will be shown even if they are zero. */
fun Double.toFixedDigitsString(maxDigits: Int = 3): String {
    return this.toBigDecimal(
        decimalMode = DecimalMode(scale = maxDigits.toLong(), roundingMode = RoundingMode.ROUND_HALF_TO_EVEN)
    ).toPlainString()
}

fun Double.toFixedDigits(maxDigits: Int = 3): Double {
    return toBigDecimal(
        decimalMode = DecimalMode(scale = maxDigits.toLong(), roundingMode = RoundingMode.ROUND_HALF_TO_EVEN)
    ).doubleValue()
}

/** Strips the decimal points digits that are zero. */
fun Double.stripZeros(maxDigits: Int = 3): String {
    val format = createDecimalFormat(maxDigits)
    return format.format(this)
    // TODO
//    return toBigDecimal(
//        decimalMode = DecimalMode(scale = maxDigits.toLong(), roundingMode = RoundingMode.ROUND_HALF_TO_EVEN)
//    )
//        // todo
//        // .stripTrailingZeros()
//        .toPlainString()
}

private fun createDecimalFormat(maxDigits: Int) = "%.${maxDigits}f"

internal fun toRadians(degrees: Double) = degrees * PI / 180.0