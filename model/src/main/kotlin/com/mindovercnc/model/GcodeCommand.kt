package com.mindovercnc.model

data class GcodeCommand(
    val id: Int,
    val name: String,
    val arguments: String,
    val rawLine: String
)