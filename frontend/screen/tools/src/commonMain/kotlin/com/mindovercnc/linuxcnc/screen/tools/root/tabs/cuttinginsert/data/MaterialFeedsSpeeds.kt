package com.mindovercnc.linuxcnc.screen.tools.root.tabs.cuttinginsert.data

import androidx.compose.ui.graphics.Color

data class MaterialFeedsSpeeds(
    val letter: Char,
    val material: String,
    val color: Color,
    val ap: ClosedFloatingPointRange<Float>? = null,
    val fn: ClosedFloatingPointRange<Float>? = null,
    val vc: IntRange? = null
)