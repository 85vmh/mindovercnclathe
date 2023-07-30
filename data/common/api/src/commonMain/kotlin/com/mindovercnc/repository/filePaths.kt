package com.mindovercnc.repository

import okio.Path
import kotlin.jvm.JvmInline

@JvmInline
value class IniFilePath(val file: Path)

@JvmInline
value class VarFilePath(val file: Path)

@JvmInline
value class ToolFilePath(val file: Path)