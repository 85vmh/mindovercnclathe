package usecase.editor

import com.mindovercnc.editor.EditorTheme
import com.mindovercnc.repository.EditorThemeRepository
import screen.composables.common.AppTheme

class EditorThemeUseCase(private val editorThemeRepository: EditorThemeRepository) {

  suspend fun getEditorTheme(): EditorTheme {
    return editorThemeRepository.getEditorTheme() ?: AppTheme.Editor.theme
  }
}
