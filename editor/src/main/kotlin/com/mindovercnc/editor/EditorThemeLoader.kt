package com.mindovercnc.editor

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.okio.decodeFromBufferedSource
import kotlinx.serialization.json.okio.encodeToBufferedSink
import okio.FileSystem
import okio.Path

@OptIn(ExperimentalSerializationApi::class)
class EditorThemeLoader(private val json: Json, private val fileSystem: FileSystem) {

  suspend fun load(file: Path): EditorTheme? {
    if (!fileSystem.exists(file)) return null
    return fileSystem.read(file) { json.decodeFromBufferedSource(EditorTheme.serializer(), this) }
  }

  suspend fun export(file: Path, theme: EditorTheme) {
    fileSystem.write(file) { json.encodeToBufferedSink(EditorTheme.serializer(), theme, this) }
  }
}
