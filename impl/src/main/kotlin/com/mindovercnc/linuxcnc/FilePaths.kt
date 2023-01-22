package com.mindovercnc.linuxcnc

import okio.Path
import okio.Path.Companion.toPath

val LinuxCncHome = System.getenv("LINUXCNC_HOME").toPath()

@JvmInline
value class IniFilePath(val file: Path)

@JvmInline
value class VarFilePath(val file: Path)

@JvmInline
value class ToolFilePath(val file: Path)