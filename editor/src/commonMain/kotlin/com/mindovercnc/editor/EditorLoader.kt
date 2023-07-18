package com.mindovercnc.editor

import com.mindovercnc.editor.reader.EditorReader
import com.mindovercnc.editor.textlines.EmptyTextLines
import okio.Path

/** Loads an editor from a [Path]. */
class EditorLoader(private val reader: EditorReader) {

  fun loadEditor(path: Path): Editor {
    return Editor(file = path) {
      try {
        with(reader) { path.readTextLines(this@Editor) }
      } catch (e: Throwable) {
        e.printStackTrace()
        EmptyTextLines
      }
    }
  }
}
