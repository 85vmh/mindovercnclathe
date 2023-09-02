package com.mindovercnc.linuxcnc.screen.tools.list.tabs.toolholder

import com.arkivanov.decompose.ComponentContext
import com.mindovercnc.linuxcnc.domain.ToolsUseCase
import com.mindovercnc.linuxcnc.screen.BaseScreenModel
import com.mindovercnc.linuxcnc.screen.tools.list.ui.ToolHolderDeleteModel
import com.mindovercnc.linuxcnc.tools.model.ToolHolder
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.instance

class HoldersToolsScreenModel(di: DI, componentContext: ComponentContext) :
    BaseScreenModel<HoldersToolsState>(HoldersToolsState(), componentContext),
    HoldersToolsComponent {

    private val toolsUseCase: ToolsUseCase by di.instance()

    init {
        toolsUseCase
            .getCurrentToolNo()
            .onEach { toolNo ->
                mutableState.update {
                    it.copy(
                        currentTool = toolNo,
                    )
                }
            }
            .launchIn(coroutineScope)

        loadToolHolders()
    }

    override fun loadToolHolders() {
        toolsUseCase
            .getToolHolders()
            .onEach { toolList ->
                mutableState.update {
                    it.copy(
                        toolHolders = toolList,
                    )
                }
            }
            .launchIn(coroutineScope)
    }

    override fun requestDeleteToolHolder(toolHolder: ToolHolder) {
        mutableState.update {
            it.copy(
                toolHolderDeleteModel = ToolHolderDeleteModel(toolHolder),
            )
        }
    }

    override fun cancelDeleteToolHolder() {
        mutableState.update { it.copy(toolHolderDeleteModel = null) }
    }

    override fun deleteToolHolder(toolHolder: ToolHolder) {
        toolsUseCase.deleteToolHolder(toolHolder)
        cancelDeleteToolHolder()
        loadToolHolders()
    }

    override fun onMountTool(toolHolder: ToolHolder) {}

    override fun loadToolHolder(toolHolder: ToolHolder) {
        coroutineScope.launch { toolsUseCase.loadTool(toolHolder.holderNumber) }
    }
}
