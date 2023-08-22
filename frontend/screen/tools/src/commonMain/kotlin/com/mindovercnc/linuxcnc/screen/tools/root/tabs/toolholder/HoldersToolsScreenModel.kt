package com.mindovercnc.linuxcnc.screen.tools.root.tabs.toolholder

import com.arkivanov.decompose.ComponentContext
import com.mindovercnc.linuxcnc.domain.ToolsUseCase
import com.mindovercnc.linuxcnc.screen.BaseScreenModel
import com.mindovercnc.linuxcnc.screen.tools.root.ui.ToolHolderDeleteModel
import com.mindovercnc.linuxcnc.tools.model.ToolHolder
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HoldersToolsScreenModel(
    private val toolsUseCase: ToolsUseCase,
    componentContext: ComponentContext
) :
    BaseScreenModel<HoldersToolsState>(HoldersToolsState(), componentContext),
    HoldersToolsComponent {

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
        mutableState.update {
            it.copy(
                toolHolderDeleteModel = null
            )
        }
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
