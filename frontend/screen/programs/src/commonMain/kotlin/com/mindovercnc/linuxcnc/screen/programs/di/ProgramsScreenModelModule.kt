package com.mindovercnc.linuxcnc.screen.programs.di

import com.mindovercnc.linuxcnc.screen.programs.programloaded.ProgramLoadedScreenModel
import com.mindovercnc.linuxcnc.screen.programs.root.ProgramsRootScreenModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

val ProgramsScreenModelModule =
    DI.Module("programs_screen_model") {
        bindProvider {
            ProgramsRootScreenModel(
                fileSystemRepository = instance(),
                fileSystem = instance(),
                editorLoader = instance(),
                fileSystemDataUseCase = instance(),
                breadCrumbDataUseCase = instance()
            )
        }

        bindProvider {
            ProgramLoadedScreenModel(
                file = instance(),
                gCodeUseCase = instance(),
                offsetsUseCase = instance(),
                positionUseCase = instance(),
                activeCodesUseCase = instance(),
                programsUseCase = instance(),
                editorLoader = instance(),
                spindleUseCase = instance(),
                feedUseCase = instance(),
                iniFileRepository = instance(),
                manualToolChangeUseCase = instance(),
                ioDispatcher = instance()
            )
        }
    }
