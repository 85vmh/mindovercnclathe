package com.mindovercnc.linuxcnc.screen.tools.list.tabs.lathetool

import com.arkivanov.decompose.ComponentContext
import com.mindovercnc.linuxcnc.domain.ToolsUseCase
import com.mindovercnc.linuxcnc.screen.BaseScreenModel
import com.mindovercnc.linuxcnc.screen.tools.list.ui.LatheToolDeleteModel
import com.mindovercnc.linuxcnc.tools.model.LatheTool
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
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
        toolsUseCase
            .getLatheTools()
            .onEach { latheTools ->
                mutableState.update {
                    it.copy(
                        latheTools = latheTools,
                    )
                }
            }
            .launchIn(coroutineScope)
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
        toolsUseCase.deleteLatheTool(latheTool)
        cancelDeleteLatheTool()
        loadLatheTools()
    }
}
