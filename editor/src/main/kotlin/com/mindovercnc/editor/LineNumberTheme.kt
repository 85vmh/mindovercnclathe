package com.mindovercnc.editor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LineNumberTheme(
  @SerialName("text") val text: IntColor,
  @SerialName("background") val background: IntColor,
  @SerialName("divider") val divider: IntColor
)
