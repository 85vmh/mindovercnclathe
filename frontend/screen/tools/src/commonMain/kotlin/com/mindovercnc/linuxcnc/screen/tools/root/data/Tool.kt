package com.mindovercnc.linuxcnc.screen.tools.root.data

data class Tool(
    val holder: Holder,
    val cutter: Cutter? = null,
    val xOffset: Double,
    val zOffset: Double,
)