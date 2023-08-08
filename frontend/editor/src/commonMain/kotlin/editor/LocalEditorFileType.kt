package editor

import androidx.compose.runtime.compositionLocalOf
import com.mindovercnc.editor.type.EditorFileType

val LocalEditorFileType = compositionLocalOf {
    EditorFileType.NORMAL
}