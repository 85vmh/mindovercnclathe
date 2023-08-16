package com.mindovercnc.model.codegen.operation

import com.mindovercnc.linuxcnc.format.toFixedDigitsString

sealed class ProfilePrimitive {

    data class Line(val type: Type, val xDest: Double? = null, val zDest: Double? = null) :
        ProfilePrimitive() {

        enum class Type(val gCode: String) {
            Traverse("G0"),
            Feed("G1")
        }

        override fun toString(): String {
            return type.gCode +
                xDest?.let { " X${it.toFixedDigitsString()}" } +
                zDest?.let { " Z${it.toFixedDigitsString()}" }
        }
    }

    data class Arc(
        val type: Type,
        val xDest: Double? = null,
        val zDest: Double? = null,
        val xOffset: Double,
        val zOffset: Double
    ) : ProfilePrimitive() {

        enum class Type(val gCode: String) {
            CW("G2"),
            CCW("G3")
        }

        override fun toString(): String {
            return type.gCode +
                xDest?.let { " X${it.toFixedDigitsString()}" } +
                zDest?.let { " Z${it.toFixedDigitsString()}" } +
                " I${xOffset.toFixedDigitsString()}" +
                " K${zOffset.toFixedDigitsString()}"
        }
    }
}
