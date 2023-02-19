package com.mindovercnc.editor.textlines

import kotlinx.coroutines.flow.StateFlow

interface TextLines {
  val size: StateFlow<Int>
  operator fun get(index: Int): TextLineContent
}
