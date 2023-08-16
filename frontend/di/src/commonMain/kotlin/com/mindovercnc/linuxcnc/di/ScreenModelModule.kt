package com.mindovercnc.linuxcnc.di

import com.mindovercnc.linuxcnc.screen.manual.di.ManualScreenModelModule
import com.mindovercnc.model.SimpleCycle
import odturning.viewmodel.OdTurningViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance
import org.kodein.di.instanceOrNull
import ui.screen.programs.programloaded.ProgramLoadedScreenModel
import ui.screen.programs.root.ProgramsRootScreenModel
import ui.screen.status.StatusRootScreenModel
import ui.screen.tools.root.ToolsScreenModel
import ui.screen.tools.root.tabs.cuttinginsert.AddEditCuttingInsertScreenModel
import ui.screen.tools.root.tabs.lathetool.AddEditLatheToolScreenModel
import ui.screen.tools.root.tabs.toolholder.AddEditToolHolderScreenModel

val ScreenModelModule =
    DI.Module("ScreenModel") {
        import(ManualScreenModelModule)

        bindProvider { OdTurningViewModel(instance()) }

        bindProvider { StatusRootScreenModel(instance()) }

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

        bindProvider { ToolsScreenModel(instance()) }

        bindProvider {
            AddEditToolHolderScreenModel(toolHolder = instanceOrNull(), toolsUseCase = instance())
        }

        bindProvider {
            AddEditLatheToolScreenModel(latheTool = instanceOrNull(), toolsUseCase = instance())
        }

        bindProvider {
            AddEditCuttingInsertScreenModel(
                cuttingInsert = instanceOrNull(),
                toolsUseCase = instance()
            )
        }
    }

fun simpleCycleModule(simpleCycle: SimpleCycle) =
    DI.Module("simpleCycle") { bindProvider("simpleCycle") { simpleCycle } }
