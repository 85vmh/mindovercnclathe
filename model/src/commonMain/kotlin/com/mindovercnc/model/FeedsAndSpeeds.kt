package com.mindovercnc.model

import kotlinx.serialization.Serializable

@Serializable
data class FeedsAndSpeeds(
    val materialName: String,
    val materialCategory: MaterialCategory,
    val ap: ClosedRange<Double>,
    val fn: ClosedRange<Double>,
    val vc: ClosedRange<Int>
)
