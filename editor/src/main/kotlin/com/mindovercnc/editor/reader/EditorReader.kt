package com.mindovercnc.editor.reader

import com.mindovercnc.editor.IntList
import com.mindovercnc.editor.textlines.TextLines
import kotlinx.coroutines.CoroutineScope
import okio.Path

interface EditorReader {
  /** Read line positions into an [IntList]. */
  fun Path.readLinePositions(starts: IntList)

  /** Reads [TextLines] from a [Path]. */
  fun Path.readTextLines(scope: CoroutineScope): TextLines
}

internal const val averageLineLength = 200
