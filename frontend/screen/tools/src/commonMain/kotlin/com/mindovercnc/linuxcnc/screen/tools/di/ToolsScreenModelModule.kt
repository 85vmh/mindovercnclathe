package com.mindovercnc.linuxcnc.screen.tools.di

import com.mindovercnc.linuxcnc.screen.tools.list.ToolsScreenModel
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.cuttinginsert.add.AddEditCuttingInsertScreenModel
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.lathetool.add.AddEditLatheToolScreenModel
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.toolholder.add.AddEditToolHolderScreenModel
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsRootScreenModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance
import org.kodein.di.instanceOrNull

val ToolsScreenModelModule =
    DI.Module("tools_screen_model") {
        bindProvider {
            ToolsScreenModel(instance(), instance())
        }

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

        bindProvider { ToolsRootScreenModel(di, instance()) }
    }
