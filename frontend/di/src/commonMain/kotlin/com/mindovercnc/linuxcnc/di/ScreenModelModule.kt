package com.mindovercnc.linuxcnc.di

import com.mindovercnc.linuxcnc.screen.conversational.di.ConversationalScreenModelModule
import com.mindovercnc.linuxcnc.screen.manual.di.ManualScreenModelModule
import com.mindovercnc.linuxcnc.screen.programs.di.ProgramsScreenModelModule
import com.mindovercnc.linuxcnc.screen.status.di.StatusScreenModelModule
import org.kodein.di.DI

val ScreenModelModule =
    DI.Module("ScreenModel") {
        importAll(
            ManualScreenModelModule,
            ProgramsScreenModelModule,
            StatusScreenModelModule,
            ConversationalScreenModelModule
        )
    }
