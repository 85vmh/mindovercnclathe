package com.mindovercnc.linuxcnc.numpad

import androidx.compose.runtime.mutableStateOf
import com.ionspin.kotlin.bignum.decimal.DecimalMode
import com.ionspin.kotlin.bignum.decimal.RoundingMode
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import com.mindovercnc.linuxcnc.format.toFixedDigitsString
import com.mindovercnc.linuxcnc.numpad.data.InputType
import com.mindovercnc.linuxcnc.numpad.data.NumInputParameters

class NumPadState constructor(
    initialValue: Double? = null,
    val numInputParameters: NumInputParameters,
    val inputType: InputType? = null,
    val onSubmitAction: (Double) -> Unit = {}
) {
    private val defaultValue = initialValue?.toFixedDigitsString(numInputParameters.maxDecimalPlaces)
        ?: numInputParameters.initialValue.toFixedDigitsString(numInputParameters.maxDecimalPlaces)

    val stringValueState = mutableStateOf(defaultValue)

    val signKey: String
        get() = if (numInputParameters.allowsNegativeValues) "+/-" else ""

    val dotKey: String
        get() = if (numInputParameters.maxDecimalPlaces > 0) "." else ""

    fun toggleSign() {
        stringValueState.value = if (stringValueState.value.startsWith('-')) {
            stringValueState.value.drop(1)
        } else {
            "-${stringValueState.value}"
        }
    }

    fun addDecimalPlace() {
        if (stringValueState.value.contains('.')) {
            return
        }
        stringValueState.value += "."
    }

    fun deleteChar() {
        if (stringValueState.value.isNotEmpty()) {
            stringValueState.value = stringValueState.value.dropLast(1)
        }
    }

    fun clearAll() {
        stringValueState.value = ""
    }
}
