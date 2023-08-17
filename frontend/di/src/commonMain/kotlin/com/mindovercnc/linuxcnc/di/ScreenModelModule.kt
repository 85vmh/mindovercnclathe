package com.mindovercnc.linuxcnc.di

import com.mindovercnc.linuxcnc.screen.manual.di.ManualScreenModelModule
import com.mindovercnc.linuxcnc.screen.programs.di.ProgramsScreenModelModule
import com.mindovercnc.linuxcnc.screen.status.StatusRootScreenModel
import com.mindovercnc.model.SimpleCycle
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance
import org.kodein.di.instanceOrNull
import ui.screen.tools.root.ToolsScreenModel
import ui.screen.tools.root.tabs.cuttinginsert.AddEditCuttingInsertScreenModel
import ui.screen.tools.root.tabs.lathetool.AddEditLatheToolScreenModel
import ui.screen.tools.root.tabs.toolholder.AddEditToolHolderScreenModel

val ScreenModelModule =
    DI.Module("ScreenModel") {
        importAll(ManualScreenModelModule, ProgramsScreenModelModule)

        bindProvider { StatusRootScreenModel(instance()) }

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
