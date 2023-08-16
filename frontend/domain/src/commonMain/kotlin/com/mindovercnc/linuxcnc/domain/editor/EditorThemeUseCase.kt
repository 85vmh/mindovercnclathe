package com.mindovercnc.linuxcnc.domain.editor

import com.mindovercnc.editor.EditorTheme
import com.mindovercnc.repository.EditorThemeRepository
import editor.theme.DefaultEditorTheme

class EditorThemeUseCase(private val editorThemeRepository: EditorThemeRepository) {

  suspend fun getEditorTheme(): EditorTheme {
    return editorThemeRepository.getEditorTheme() ?: DefaultEditorTheme.theme
  }
}
