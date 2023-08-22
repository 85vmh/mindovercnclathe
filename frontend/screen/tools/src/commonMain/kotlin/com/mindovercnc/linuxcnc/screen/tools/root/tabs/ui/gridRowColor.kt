package com.mindovercnc.linuxcnc.screen.tools.root.tabs.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun gridRowColorFor(index: Int): Color {
    return if (index % 2 == 0) Color(0xFFeeeeee)
    else Color(0xeeeeeeee)
}