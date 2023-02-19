package com.mindovercnc.editor.type

import okio.Path

/** Implementation for [EditorFileTypeHandler]. */
object EditorFileTypeHandlerImpl : EditorFileTypeHandler {
  override fun getFileType(path: Path): EditorFileType {
    val extension = path.extension?.lowercase() ?: return EditorFileType.NORMAL
    return when (extension) {
      "ngc",
      "nc" -> EditorFileType.GCODE
      else -> EditorFileType.NORMAL
    }
  }

  private val Path.extension: String?
    get() = segments.lastOrNull()?.substringAfterLast('.', "")
}
