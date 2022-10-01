package screen.composables.common

import androidx.compose.material.darkColors
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import com.mindovercnc.editor.EditorTheme
import com.mindovercnc.editor.IntColor

object AppTheme {

    object Editor {
        val light = EditorTheme(
            comment = IntColor(0xFF808080),
            variable = IntColor(0xFF696969),
            value = IntColor(0xFF6897BB),
            text = IntColor(0xFFA9B7C6),
            keyword = IntColor(0xFFCC7832),
            punctuation = IntColor(0xFFA1C17E),
            background = IntColor(0xFFFFFFFF),
            lineNumber = IntColor(0xFFCECECE),
            gcode = IntColor(0XFFAAAAFF)
        )
        val dark = EditorTheme(
            comment = IntColor(0xFF808080),
            variable = IntColor(0xFFCCCCCC),
            value = IntColor(0xFF6897BB),
            text = IntColor(0xFFA9B7C6),
            keyword = IntColor(0xFFCC7832),
            punctuation = IntColor(0xFFA1C17E),
            background = IntColor(0xFF2B2B2B),
            lineNumber = IntColor(0xFFCECECE),
            gcode = IntColor(0XFF6666FF)
        )
    }

}