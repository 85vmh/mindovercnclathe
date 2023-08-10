package com.mindovercnc.linuxcnc.gcode.local.di

import com.mindovercnc.linuxcnc.gcode.GCodeRepository
import com.mindovercnc.linuxcnc.gcode.IniFileRepository
import com.mindovercnc.linuxcnc.gcode.local.GCodeRepositoryLocal
import com.mindovercnc.linuxcnc.gcode.local.IniFileRepositoryLocal
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val GCodeLocalModule = DI.Module("gcode_local") {
    bindSingleton<GCodeRepository> { GCodeRepositoryLocal(instance(), instance(), instance()) }
    bindSingleton<IniFileRepository> { IniFileRepositoryLocal(instance()) }
}