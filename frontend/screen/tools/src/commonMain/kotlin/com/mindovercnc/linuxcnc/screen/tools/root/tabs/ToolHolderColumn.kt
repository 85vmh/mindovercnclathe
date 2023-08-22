package com.mindovercnc.linuxcnc.screen.tools.root.tabs

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal enum class ToolHolderColumn(val text: String, val size: Dp = Dp.Unspecified) {
    Id("ID", 50.dp),
    HolderType("Type", 100.dp),
    Offsets("Tool Offsets", 120.dp),
    ToolInfo("Mounted Tool"),
    Actions("Actions", 210.dp)
}
