package com.mindovercnc.model

data class G53AxisLimits(
    val xMinLimit: Double? = null,
    val xMaxLimit: Double? = null,
    val zMinLimit: Double? = null,
    val zMaxLimit: Double? = null
)

fun G53AxisLimits.inSafeRange(value: Double = 0.0001): G53AxisLimits {
    return G53AxisLimits(
        xMinLimit = xMinLimit?.plus(value),
        xMaxLimit = xMaxLimit?.minus(value),
        zMinLimit = zMinLimit?.plus(value),
        zMaxLimit = zMaxLimit?.minus(value),
    )
}
