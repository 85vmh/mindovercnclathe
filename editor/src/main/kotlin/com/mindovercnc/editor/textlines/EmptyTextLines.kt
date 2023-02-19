package com.mindovercnc.editor.textlines

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/** Empty implementation for [TextLines]. */
object EmptyTextLines : TextLines {
  override val size: StateFlow<Int> = MutableStateFlow(0)

  override fun get(index: Int) = TextLineContent(1, "")
}
