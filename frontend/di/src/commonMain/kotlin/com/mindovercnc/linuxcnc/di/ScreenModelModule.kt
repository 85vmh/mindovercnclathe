package com.mindovercnc.linuxcnc.di

import com.mindovercnc.linuxcnc.screen.manual.di.ManualScreenModelModule
import com.mindovercnc.linuxcnc.screen.programs.di.ProgramsScreenModelModule
import com.mindovercnc.linuxcnc.screen.status.di.StatusScreenModelModule
import com.mindovercnc.linuxcnc.screen.tools.di.ToolsScreenModelModule
import com.mindovercnc.model.SimpleCycle
import org.kodein.di.DI
import org.kodein.di.bindProvider

val ScreenModelModule =
    DI.Module("ScreenModel") {
        importAll(
            ManualScreenModelModule,
            ProgramsScreenModelModule,
            ToolsScreenModelModule,
            StatusScreenModelModule
        )
    }

fun simpleCycleModule(simpleCycle: SimpleCycle) =
    DI.Module("simpleCycle") { bindProvider("simpleCycle") { simpleCycle } }
