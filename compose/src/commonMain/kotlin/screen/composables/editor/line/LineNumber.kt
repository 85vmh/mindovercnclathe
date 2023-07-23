package screen.composables.editor.line

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import screen.composables.common.Fonts
import screen.composables.common.Settings
import screen.composables.editor.LocalEditorTheme
import screen.composables.editor.toColor

@Composable
internal fun LineNumber(number: String, settings: Settings, modifier: Modifier = Modifier) {
  Text(
    text = number,
    fontSize = settings.fontSize,
    fontFamily = Fonts.jetbrainsMono(),
    color = LocalEditorTheme.current.lineNumber.text.toColor(),
    modifier = modifier
  )
}
