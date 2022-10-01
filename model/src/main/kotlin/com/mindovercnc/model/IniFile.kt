package com.mindovercnc.model

import java.io.File

data class IniFile(
    val subroutinePath: String,
    val programDir: File,
    val parameterFile: File,
    val toolTableFile: File,
    val joints: List<JointParameters>
) {

    data class JointParameters(
        val minLimit: Double,
        val maxLimit: Double,
        val home: Double,
        val homeOffset: Double
    )
}