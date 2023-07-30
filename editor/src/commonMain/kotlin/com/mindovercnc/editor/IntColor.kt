package com.mindovercnc.editor

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Serializable
value class IntColor(val value: Long)