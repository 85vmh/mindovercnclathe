package com.mindovercnc.linuxcnc.screen.tools.root.tabs

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class CuttingInsertsColumns(val text: String, val size: Dp = Dp.Unspecified) {
    Id("ID", 50.dp),
    MadeOf("Made Of"),
    Code("Code"),
    TipRadius("Tip Radius"),
    TipAngle("Tip Angle"),
    Size("Size"),
    Materials("Materials", 400.dp),
    Actions("Actions", 140.dp),
}