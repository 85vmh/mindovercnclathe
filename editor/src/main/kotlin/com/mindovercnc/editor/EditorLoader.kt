package com.mindovercnc.editor

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import java.io.File

class EditorLoader(
    private val json: Json
) {

    suspend fun load(file: File): EditorTheme {
        return file.inputStream().use {
            json.decodeFromStream(EditorTheme.serializer(), it)
        }
    }

    suspend fun export(file: File, theme: EditorTheme) {
        file.outputStream().use {
            json.encodeToStream(EditorTheme.serializer(), theme, it)
        }
    }

}