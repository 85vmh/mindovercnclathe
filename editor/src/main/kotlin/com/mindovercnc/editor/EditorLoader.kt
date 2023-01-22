package com.mindovercnc.editor

import com.mindovercnc.model.EmptyTextLines
import com.mindovercnc.model.TextLines
import com.mindovercnc.model.extension
import com.mindovercnc.model.readTextLines
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import okio.FileSystem
import okio.Path

class EditorLoader(private val fileSystem: FileSystem) {

  fun loadEditor(file: Path): Editor {
    return Editor(file = file, fileName = file.name) {
      val extension = file.extension
      val textLines: TextLines =
        try {
          file.readTextLines(this)
        } catch (e: Throwable) {
          e.printStackTrace()
          EmptyTextLines
        }
      val isGCode =
        extension.endsWith("ngc", ignoreCase = true) || extension.endsWith("nc", ignoreCase = true)

      fun content(index: Int): Editor.Content {
        val text = textLines.get(index)
        val state = MutableStateFlow(text).asStateFlow()
        return Editor.Content(state, isGCode)
      }

      object : Editor.Lines {
        override val size
          get() = textLines.size

        override fun get(index: Int) = Editor.Line(number = index + 1, content = content(index))
      }
    }
  }

  private fun Path.readTextLines() {

  }
}
