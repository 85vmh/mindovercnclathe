package com.mindovercnc.linuxcnc.screen.programs.di

import com.mindovercnc.linuxcnc.screen.programs.picker.ProgramPickerScreenModel
import com.mindovercnc.linuxcnc.screen.programs.programloaded.ProgramLoadedScreenModel
import com.mindovercnc.linuxcnc.screen.programs.root.ProgramsRootScreenModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

@Deprecated("Use constructor instead")
val ProgramsScreenModelModule =
    DI.Module("programs_screen_model") {
        bindProvider { ProgramPickerScreenModel(di, componentContext = instance()) }

        bindProvider { ProgramsRootScreenModel(di, componentContext = instance()) }

        bindProvider { ProgramLoadedScreenModel(di, componentContext = instance()) }
    }
