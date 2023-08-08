package di

import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance
import org.kodein.di.instanceOrNull
import screen.uimodel.SimpleCycle
import screen.viewmodel.OdTurningViewModel
import ui.screen.manual.root.ManualTurningScreenModel
import ui.screen.manual.simplecycles.SimpleCyclesScreenModel
import ui.screen.manual.tapersettings.TaperSettingsScreenModel
import ui.screen.manual.turningsettings.TurningSettingsScreenModel
import ui.screen.manual.virtuallimits.VirtualLimitsScreenModel
import ui.screen.programs.programloaded.ProgramLoadedScreenModel
import ui.screen.programs.root.ProgramsRootScreenModel
import ui.screen.status.StatusRootScreenModel
import ui.screen.tools.root.ToolsScreenModel
import ui.screen.tools.root.tabs.cuttinginsert.AddEditCuttingInsertScreenModel
import ui.screen.tools.root.tabs.lathetool.AddEditLatheToolScreenModel
import ui.screen.tools.root.tabs.toolholder.AddEditToolHolderScreenModel

val ScreenModelModule =
  DI.Module("ScreenModel") {
    bindProvider { OdTurningViewModel(instance()) }

    bindProvider {
      ManualTurningScreenModel(
        instance(),
        instance(),
        instance(),
        instance(),
        instance(),
        instance(),
        instance(),
        instance(),
        instance()
      )
    }

    bindProvider { TurningSettingsScreenModel(instance()) }

    bindProvider { TaperSettingsScreenModel(instance()) }

    bindProvider { VirtualLimitsScreenModel(instance(), instance()) }

    bindProvider {
      SimpleCyclesScreenModel(
        simpleCycle = instance(),
        positionUseCase = instance(),
        simpleCyclesUseCase = instance()
      )
    }

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
      AddEditCuttingInsertScreenModel(cuttingInsert = instanceOrNull(), toolsUseCase = instance())
    }
  }

fun simpleCycleModule(simpleCycle: SimpleCycle) =
  DI.Module("simpleCycle") { bindProvider("simpleCycle") { simpleCycle } }
