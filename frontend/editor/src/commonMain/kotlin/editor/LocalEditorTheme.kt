package editor

import androidx.compose.runtime.compositionLocalOf
import editor.theme.DefaultEditorTheme

val LocalEditorTheme = compositionLocalOf { DefaultEditorTheme.theme.dark }
