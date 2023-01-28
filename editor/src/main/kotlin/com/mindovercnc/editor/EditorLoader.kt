package com.mindovercnc.editor

import com.mindovercnc.dispatchers.IoDispatcher
import java.io.FileInputStream
import java.io.IOException
import java.io.RandomAccessFile
import java.nio.channels.FileChannel
import java.nio.charset.StandardCharsets
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.FileSystem
import okio.Path

class EditorLoader(private val fileSystem: FileSystem, private val ioDispatcher: IoDispatcher) {

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

  /** Reads [TextLines] from a [Path]. */
  @Deprecated(
    "Refactor using [FileSystem] for multiplatform support.",
    level = DeprecationLevel.WARNING
  )
  private fun Path.readTextLines(scope: CoroutineScope): TextLines {
    var byteBufferSize: Int
    val byteBuffer =
      RandomAccessFile(this.toFile(), "r").use { file ->
        byteBufferSize = file.length().toInt()
        file.channel.map(FileChannel.MapMode.READ_ONLY, 0, file.length())
      }

    val lineStartPositions = IntList()

    var size = 0

    val refreshJob =
      scope.launch {
        delay(100)
        size = lineStartPositions.size
        while (true) {
          delay(1000)
          size = lineStartPositions.size
        }
      }

    scope.launch(Dispatchers.IO) {
      readLinePositions(lineStartPositions)
      refreshJob.cancel()
      size = lineStartPositions.size
    }

    return object : TextLines {
      override val size
        get() = size

      override fun get(index: Int): String {
        val startPosition = lineStartPositions[index]
        val length =
          if (index + 1 < size) lineStartPositions[index + 1] - startPosition
          else byteBufferSize - startPosition
        // Only JDK since 13 has slice() method we need, so do ugly for now.
        byteBuffer.position(startPosition)
        val slice = byteBuffer.slice()
        slice.limit(length)
        return StandardCharsets.UTF_8.decode(slice).toString()
      }
    }
  }

  /** Read line positions into an [IntList]. */
  @Deprecated(
    "Refactor using [FileSystem] for multiplatform support.",
    level = DeprecationLevel.WARNING
  )
  private fun Path.readLinePositions(starts: IntList) {
    val file = toFile()
    require(file.length() <= Int.MAX_VALUE) {
      "Files with size over ${Int.MAX_VALUE} aren't supported"
    }

    val averageLineLength = 200
    starts.clear(file.length().toInt() / averageLineLength)

    try {
      FileInputStream(file).use {
        val channel = it.channel
        val ib = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size())
        var isBeginOfLine = true
        var position = 0L
        while (ib.hasRemaining()) {
          val byte = ib.get()
          if (isBeginOfLine) {
            starts.add(position.toInt())
          }
          isBeginOfLine = byte.toInt().toChar() == '\n'
          position++
        }
        channel.close()
      }
    } catch (e: IOException) {
      e.printStackTrace()
      starts.clear(1)
      starts.add(0)
    }

    starts.compact()
  }

  private val Path.extension: String
    get() = segments.last().substringAfterLast('.', "")
}
