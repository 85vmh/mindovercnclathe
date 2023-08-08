package editor.line

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import editor.EditorSettings
import editor.LocalEditorTheme
import editor.toColor

@Composable
internal fun LineNumber(number: String, settings: EditorSettings, modifier: Modifier = Modifier) {
    Text(
        text = number,
        fontSize = settings.fontSize,
        fontFamily = FontFamily.Monospace,
        color = LocalEditorTheme.current.lineNumber.text.toColor(),
        modifier = modifier
    )
}
