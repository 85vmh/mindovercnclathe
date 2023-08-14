package com.mindovercnc.linuxcnc.numpad.data

interface NumInputParameters {
    val description: String
    val unit: UnitType?
    val minValue: Double
    val maxValue: Double
    val allowsNegativeValues: Boolean
    val maxDecimalPlaces: Int
    val initialValue: Double
}
