package com.mindovercnc.data.linuxcnc.model

import kotlinx.datetime.Instant
import okio.Path

data class FileResponse(
    val name: String,
    val isDirectory: Boolean,
    val lastModified: Instant?,
    val path: Path
)