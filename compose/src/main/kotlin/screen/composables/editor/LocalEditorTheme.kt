package screen.composables.editor

import androidx.compose.runtime.compositionLocalOf
import screen.composables.common.AppTheme

val LocalEditorTheme = compositionLocalOf {
    AppTheme.Editor.light
}