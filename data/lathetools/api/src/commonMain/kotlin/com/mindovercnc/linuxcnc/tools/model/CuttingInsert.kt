package com.mindovercnc.linuxcnc.tools.model

import kotlinx.serialization.Serializable

@Serializable
data class CuttingInsert(
    val id: Int? = null,
    val madeOf: MadeOf,
    val code: String?,
    val tipRadius: Double,
    val tipAngle: Double,
    val size: Double
) {
    enum class Type {
        Positive,
        Negative
    }
}
