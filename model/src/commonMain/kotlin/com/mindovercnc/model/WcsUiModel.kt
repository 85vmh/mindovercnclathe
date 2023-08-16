package com.mindovercnc.model

data class WcsUiModel(
    val activeOffset: String,
    val wcsOffsets: List<WcsOffset>,
) {
    val selected by lazy { wcsOffsets.firstOrNull { it.coordinateSystem == activeOffset } }
}
