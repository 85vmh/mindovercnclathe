package com.mindovercnc.editor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EditorTheme(
  @SerialName("light") val light: EditorThemeVariant,
  @SerialName("dark") val dark: EditorThemeVariant
)
