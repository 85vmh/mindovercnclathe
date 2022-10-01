package screen.composables.editor

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import com.mindovercnc.editor.IntColor

fun IntColor.toColor() = Color(value)

fun IntColor.toSpanStyle() = SpanStyle(toColor())