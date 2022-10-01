package com.mindovercnc.repository

import com.mindovercnc.model.GcodeCommand
import java.io.File

interface GCodeRepository {
    fun parseFile(file: File): List<GcodeCommand>
}