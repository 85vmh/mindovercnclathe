package com.mindovercnc.linuxcnc.screen.tools.root

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.mindovercnc.linuxcnc.domain.ToolsUseCase
import com.mindovercnc.linuxcnc.screen.tools.root.ui.CuttingInsertDeleteModel
import com.mindovercnc.linuxcnc.screen.tools.root.ui.LatheToolDeleteModel
import com.mindovercnc.linuxcnc.screen.tools.root.ui.ToolHolderDeleteModel
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert
import com.mindovercnc.linuxcnc.tools.model.LatheTool
import com.mindovercnc.linuxcnc.tools.model.ToolHolder
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ToolsScreenModel(
    private val toolsUseCase: ToolsUseCase,
) : StateScreenModel<ToolsState>(ToolsState()), ToolsComponent {

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
        loadLatheTools()
        loadCuttingInserts()
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

    override fun loadCuttingInserts() {
        toolsUseCase
            .getCuttingInserts()
            .onEach { insertsList -> mutableState.update { it.copy(cuttingInserts = insertsList) } }
            .launchIn(coroutineScope)
    }

    override fun selectTab(tab: ToolsTabItem) {
        mutableState.update { it.copy(currentTab = tab) }
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
                toolHolderDeleteModel = null,
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

    override fun deleteCuttingInsert(insert: CuttingInsert) {
        toolsUseCase.deleteCuttingInsert(insert)
        cancelDeleteCuttingInsert()
        loadCuttingInserts()
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

    override fun requestDeleteCuttingInsert(cuttingInsert: CuttingInsert) {
        mutableState.update {
            it.copy(
                cuttingInsertDeleteModel = CuttingInsertDeleteModel(cuttingInsert),
            )
        }
    }

    override fun cancelDeleteCuttingInsert() {
        mutableState.update {
            it.copy(
                cuttingInsertDeleteModel = null,
            )
        }
    }
}
