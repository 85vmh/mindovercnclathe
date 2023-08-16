package com.mindovercnc.model

data class FeedUiModel(
    val isUnitsPerRevMode: Boolean = true,
    val feedOverride: Double = 0.0,
    val setFeed: Double = 0.0,
    val actualFeed: Double = 0.0,
    val units: String = "mm",
)
