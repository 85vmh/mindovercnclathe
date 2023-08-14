package com.mindovercnc.linuxcnc.format

import com.ionspin.kotlin.bignum.decimal.DecimalMode
import com.ionspin.kotlin.bignum.decimal.RoundingMode
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import mu.KotlinLogging
import kotlin.math.PI
import kotlin.math.roundToInt

private val LOG = KotlinLogging.logger("DecimalFormat")

fun Double.toPercent(): Int = (this * 100).roundToInt()

/** Sets the displayable digits to max digits, which will be shown even if they are zero. */
fun Double.toFixedDigitsString(maxDigits: Int = 3): String {
    return this.toBigDecimal(
            decimalMode =
                DecimalMode(
                    decimalPrecision = maxDigits.toLong(),
                    roundingMode = RoundingMode.ROUND_HALF_TO_EVEN,
                    scale = maxDigits.toLong()
                )
        )
        .toPlainString()
        .also { result -> LOG.info { "$this toFixedDigitsString $result" } }
}

fun Double.toFixedDigits(maxDigits: Int = 3): Double {
    return toBigDecimal(
            decimalMode =
                DecimalMode(
                    scale = maxDigits.toLong(),
                    roundingMode = RoundingMode.ROUND_HALF_TO_EVEN
                )
        )
        .doubleValue()
}

/** Strips the decimal points digits that are zero. */
fun Double.stripZeros(maxDigits: Int = 3): String {
    return toBigDecimal(
            decimalMode =
                DecimalMode(
                    scale = maxDigits.toLong(),
                    roundingMode = RoundingMode.ROUND_HALF_TO_EVEN
                )
        )
        // todo
        // .stripTrailingZeros()
        .toPlainString()
}

/** Strips the decimal points digits that are zero. */
fun Float.stripZeros(maxDigits: Int = 3): String {
    return toBigDecimal(
            decimalMode =
                DecimalMode(
                    scale = maxDigits.toLong(),
                    roundingMode = RoundingMode.ROUND_HALF_TO_EVEN
                )
        )
        // todo
        // .stripTrailingZeros()
        .toPlainString()
}

fun toRadians(degrees: Double) = degrees * PI / 180.0
