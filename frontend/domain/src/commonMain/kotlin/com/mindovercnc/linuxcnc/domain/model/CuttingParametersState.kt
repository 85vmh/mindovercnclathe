package com.mindovercnc.linuxcnc.domain.model

import androidx.compose.runtime.mutableStateOf

class CuttingParametersState(
    toolNo: Int,
    cssSpeed: Int,
    feed: Double,
    doc: Double
) {
    val toolNo = mutableStateOf(toolNo)
    val cssSpeed = mutableStateOf(cssSpeed)
    val feed = mutableStateOf(feed)
    val doc = mutableStateOf(doc)
}