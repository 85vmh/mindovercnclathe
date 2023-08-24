package com.mindovercnc.linuxcnc.screen.root

data class RootState(
    val isBottomBarEnabled: Boolean = true,
    val currentTool: Int = 0,
)