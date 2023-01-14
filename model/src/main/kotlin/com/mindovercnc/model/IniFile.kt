package com.mindovercnc.model

import okio.Path

data class IniFile constructor(
    val subroutinePath: String,
    val programDir: Path,
    val parameterFile: Path,
    val toolTableFile: Path,
    val joints: List<JointParameters>
) {

    data class JointParameters(
        val minLimit: Double,
        val maxLimit: Double,
        val home: Double,
        val homeOffset: Double
    )
}