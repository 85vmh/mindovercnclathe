package com.mindovercnc.editor

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okio.Path

class Editor(
  val file: Path,
  val fileName: String,
  val lines: CoroutineScope.() -> Lines,
) {
  var close: (() -> Unit)? = null

  val selected = MutableStateFlow<Any?>(null)

  val isActive: Boolean
    get() = selected.value === this

  fun activate() {
    selected.value = this
  }

  class Line(val number: Int, val content: Content)

  interface Lines {
    val lineNumberDigitCount: Int
      get() = size.toString().length
    val size: Int
    operator fun get(index: Int): Line
  }

  class Content(val value: StateFlow<String>, val isGCode: Boolean)
}
