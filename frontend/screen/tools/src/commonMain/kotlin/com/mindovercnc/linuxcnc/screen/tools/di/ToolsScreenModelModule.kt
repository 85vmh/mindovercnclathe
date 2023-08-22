package com.mindovercnc.linuxcnc.screen.tools.di

import com.mindovercnc.linuxcnc.screen.tools.root.ToolsScreenModel
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.cuttinginsert.AddEditCuttingInsertScreenModel
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.lathetool.AddEditLatheToolScreenModel
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.toolholder.add.AddEditToolHolderScreenModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance
import org.kodein.di.instanceOrNull

val ToolsScreenModelModule =
    DI.Module("tools_screen_model") {
        bindProvider { ToolsScreenModel(instance(), instance()) }

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
