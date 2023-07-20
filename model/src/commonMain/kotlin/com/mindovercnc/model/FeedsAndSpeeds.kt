package com.mindovercnc.model

data class FeedsAndSpeeds(
    val materialName: String,
    val materialCategory: MaterialCategory,
    val ap: ClosedFloatingPointRange<Float>,
    val fn: ClosedFloatingPointRange<Float>,
    val vc: IntRange
)
