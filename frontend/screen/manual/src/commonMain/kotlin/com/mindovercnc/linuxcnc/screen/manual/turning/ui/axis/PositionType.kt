package com.mindovercnc.linuxcnc.screen.manual.turning.ui.axis

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class PositionType(val fontSize: TextUnit, val width: Dp) {
  PRIMARY(50.sp, 300.dp),
  SECONDARY(18.sp, 110.dp),
}
