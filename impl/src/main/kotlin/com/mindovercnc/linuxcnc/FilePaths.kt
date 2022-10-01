package com.mindovercnc.linuxcnc

import java.io.File

val LinuxCncHome = File(System.getenv("LINUXCNC_HOME"))

@JvmInline
value class IniFilePath(val file: File)

@JvmInline
value class VarFilePath(val file: File)

@JvmInline
value class ToolFilePath(val file: File)