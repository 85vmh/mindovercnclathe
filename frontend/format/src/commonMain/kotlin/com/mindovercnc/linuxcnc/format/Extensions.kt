package com.mindovercnc.linuxcnc.format

import com.ionspin.kotlin.bignum.decimal.DecimalMode
import com.ionspin.kotlin.bignum.decimal.RoundingMode
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import kotlin.math.PI
import kotlin.math.roundToInt

fun Double.toPercent(): Int = (this * 100).roundToInt()

fun Number.formatMaxDecimals(maxDigits: Int): String {
    return this.toDouble()
        .toBigDecimal(
            decimalMode =
                DecimalMode(
                    decimalPrecision = maxDigits.toLong(),
                    roundingMode = RoundingMode.ROUND_HALF_TO_EVEN
                )
        )
        .toPlainString()
}

/** Sets the displayable digits to max digits, which will be shown even if they are zero. */
fun Number.toFixedDigitsString(maxDigits: Int = 3): String {
    return this.toDouble()
        .toBigDecimal(
            decimalMode =
                DecimalMode(
                    decimalPrecision = maxDigits.toLong(),
                    roundingMode = RoundingMode.ROUND_HALF_TO_EVEN,
                    scale = maxDigits.toLong()
                )
        )
        .toPlainString()
    // .also { result -> LOG.info { "$this toFixedDigitsString $result" } }
}

fun Number.toFixedDigits(maxDigits: Int = 3): Double {
    return toDouble()
        .toBigDecimal(
            decimalMode =
                DecimalMode(
                    scale = maxDigits.toLong(),
                    roundingMode = RoundingMode.ROUND_HALF_TO_EVEN
                )
        )
        .doubleValue()
}

/** Strips the decimal points digits that are zero. */
fun Number.stripZeros(maxDigits: Int = 3): String {
    return toDouble()
        .toBigDecimal(
            decimalMode =
                DecimalMode(
                    decimalPrecision = maxDigits.toLong(),
                    roundingMode = RoundingMode.ROUND_HALF_TO_EVEN
                )
        )
        .toPlainString()
}

fun toRadians(degrees: Double) = degrees * PI / 180.0
