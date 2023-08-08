package editor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp

class EditorSettings {
    var fontSize by mutableStateOf(13.sp)
    val maxLineSymbols = 120
}