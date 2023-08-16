package com.mindovercnc.linuxcnc.domain.model

import androidx.compose.runtime.mutableStateOf

class AngleFinderState(
    traverseOnZ: Boolean,
    procedureStarted: Boolean,
    taperAngle: Double,
) {
    val taperAngle = mutableStateOf(taperAngle)
    val procedureStarted = mutableStateOf(procedureStarted)
    val traverseOnZ = mutableStateOf(traverseOnZ)
}