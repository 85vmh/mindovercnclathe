package com.mindovercnc.linuxcnc.numpad.data

interface NumInputParameters {
    val description: String
    val unit: String?
    val minValue: Double
    val maxValue: Double
    val allowsNegativeValues: Boolean
    val maxDecimalPlaces: Int
    val initialValue: Double
}