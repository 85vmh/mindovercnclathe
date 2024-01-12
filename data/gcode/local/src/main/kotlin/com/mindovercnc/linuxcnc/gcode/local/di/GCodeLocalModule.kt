package com.mindovercnc.linuxcnc.gcode.local.di

import com.mindovercnc.linuxcnc.gcode.GCodeInterpreterRepository
import com.mindovercnc.linuxcnc.gcode.local.GCodeInterpreterRepositoryLocal
import com.mindovercnc.linuxcnc.gcode.local.GcodeParser
import com.mindovercnc.linuxcnc.gcode.local.GcodeReader
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val GCodeLocalModule =
    DI.Module("gcode_local") {
        bindSingleton { GcodeParser() }
        bindSingleton { GcodeReader(instance(), instance(), instance(), instance(), instance()) }
        bindSingleton<GCodeInterpreterRepository> { GCodeInterpreterRepositoryLocal(instance()) }
    }
