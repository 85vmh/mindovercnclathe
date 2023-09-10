package com.mindovercnc.linuxcnc.screen.tools.list.tabs.lathetool

import com.arkivanov.decompose.ComponentContext
import com.mindovercnc.linuxcnc.domain.ToolsUseCase
import com.mindovercnc.linuxcnc.screen.BaseScreenModel
import com.mindovercnc.linuxcnc.screen.tools.list.ui.LatheToolDeleteModel
import com.mindovercnc.linuxcnc.tools.model.LatheTool
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.instance

class LatheToolsScreenModel(
    di: DI,
    componentContext: ComponentContext,
) : BaseScreenModel<LatheToolsState>(LatheToolsState(), componentContext), LatheToolsComponent {

    private val toolsUseCase: ToolsUseCase by di.instance()

    init {
        loadLatheTools()
    }

    override fun loadLatheTools() {
        coroutineScope.launch {
            val latheTools = toolsUseCase.getLatheTools()

            mutableState.update { it.copy(latheTools = latheTools) }
        }
    }

    override fun requestDeleteLatheTool(latheTool: LatheTool) {
        mutableState.update {
            it.copy(
                latheToolDeleteModel = LatheToolDeleteModel(latheTool),
            )
        }
    }

    override fun cancelDeleteLatheTool() {
        mutableState.update {
            it.copy(
                latheToolDeleteModel = null,
            )
        }
    }

    override fun deleteLatheTool(latheTool: LatheTool) {
        coroutineScope.launch {
            toolsUseCase.deleteLatheTool(latheTool)
            cancelDeleteLatheTool()
            loadLatheTools()
        }
    }
}
