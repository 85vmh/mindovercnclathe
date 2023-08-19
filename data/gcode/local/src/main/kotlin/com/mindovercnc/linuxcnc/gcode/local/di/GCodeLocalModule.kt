package com.mindovercnc.linuxcnc.gcode.local.di

import com.mindovercnc.linuxcnc.gcode.GCodeInterpreterRepository
import com.mindovercnc.linuxcnc.gcode.local.GCodeInterpreterRepositoryLocal
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val GCodeLocalModule = DI.Module("gcode_local") {
    bindSingleton<GCodeInterpreterRepository> { GCodeInterpreterRepositoryLocal(instance(), instance(), instance()) }
}