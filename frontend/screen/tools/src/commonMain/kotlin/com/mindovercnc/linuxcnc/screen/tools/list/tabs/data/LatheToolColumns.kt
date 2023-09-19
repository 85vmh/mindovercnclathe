package com.mindovercnc.linuxcnc.screen.tools.list.tabs.data

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal enum class LatheToolColumns(val text: String, val size: Dp = Dp.Unspecified) {
    Id("ID", 50.dp),
    ToolType("Type", 100.dp),
    Details("Tool Details"),
    Orientation("Orient", 60.dp),
    Rotation("Spindle", 60.dp),
    Usage("Usage", 100.dp),
    Actions("Actions", 140.dp),
}
