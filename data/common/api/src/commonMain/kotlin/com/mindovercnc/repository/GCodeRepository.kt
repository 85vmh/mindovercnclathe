package com.mindovercnc.repository

import com.mindovercnc.model.GcodeCommand
import okio.Path

interface GCodeRepository {
    fun parseFile(file: Path): List<GcodeCommand>
}