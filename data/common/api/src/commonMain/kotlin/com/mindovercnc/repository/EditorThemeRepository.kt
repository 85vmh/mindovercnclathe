package com.mindovercnc.repository

import com.mindovercnc.editor.EditorTheme
import com.mindovercnc.editor.EditorThemeVariant

/** Repository for [EditorThemeVariant]. */
interface EditorThemeRepository {
  suspend fun getEditorTheme(): EditorTheme?
}
