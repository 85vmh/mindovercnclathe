package com.mindovercnc.model

data class Point2D(
    val x: Double,
    val z: Double
) {
    companion object {
        val zero = Point2D(0.0, 0.0)
    }
}
