package com.mindovercnc.linuxcnc.tools.model

import kotlinx.serialization.Serializable

@Serializable
data class ToolHolder(
    val holderNumber: Int,
    val type: ToolHolderType,
    val latheTool: LatheTool? = null,
    val clampingPosition: Int = 0,
    val xOffset: Double? = null,
    val zOffset: Double? = null,
)
