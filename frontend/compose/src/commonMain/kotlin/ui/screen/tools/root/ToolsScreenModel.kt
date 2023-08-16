package ui.screen.tools.root

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.mindovercnc.linuxcnc.domain.ToolsUseCase
import com.mindovercnc.model.CuttingInsert
import com.mindovercnc.model.LatheTool
import com.mindovercnc.model.tool.ToolHolder
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import screen.composables.ToolsTabItem

class ToolsScreenModel(
    private val toolsUseCase: ToolsUseCase
) : StateScreenModel<ToolsScreenModel.State>(State()) {

    data class State(
        val currentTab: ToolsTabItem = ToolsTabItem.ToolHolders,
        val toolHolders: List<ToolHolder> = emptyList(),
        val latheTools: List<LatheTool> = emptyList(),
        val cuttingInserts: List<CuttingInsert> = emptyList(),
        val currentTool: Int = 0,
        val toolHolderDeleteModel: ToolHolderDeleteModel? = null,
        val latheToolDeleteModel: LatheToolDeleteModel? = null,
        val cuttingInsertDeleteModel: CuttingInsertDeleteModel? = null,
    )

    init {
        toolsUseCase.getCurrentToolNo()
            .onEach { toolNo ->
                mutableState.update {
                    it.copy(
                        currentTool = toolNo,
                    )
                }
            }.launchIn(coroutineScope)

        loadToolHolders()
        loadLatheTools()
        loadCuttingInserts()
    }

    fun loadToolHolders() {
        toolsUseCase.getToolHolders()
            .onEach { toolList ->
                mutableState.update {
                    it.copy(
                        toolHolders = toolList,
                    )
                }
            }.launchIn(coroutineScope)
    }

    fun loadLatheTools() {
        toolsUseCase.getLatheTools()
            .onEach { latheTools ->
                mutableState.update {
                    it.copy(
                        latheTools = latheTools,
                    )
                }
            }.launchIn(coroutineScope)
    }

    fun loadCuttingInserts() {
        toolsUseCase.getCuttingInserts()
            .onEach { insertsList ->
                mutableState.update {
                    it.copy(
                        cuttingInserts = insertsList
                    )
                }
            }.launchIn(coroutineScope)
    }

    fun selectTab(tab: ToolsTabItem) {
        mutableState.update {
            it.copy(currentTab = tab)
        }
    }

    fun requestDeleteToolHolder(toolHolder: ToolHolder) {
        mutableState.update {
            it.copy(
                toolHolderDeleteModel = ToolHolderDeleteModel(toolHolder),
            )
        }
    }

    fun cancelDeleteToolHolder() {
        mutableState.update {
            it.copy(
                toolHolderDeleteModel = null,
            )
        }
    }

    fun deleteToolHolder(toolHolder: ToolHolder) {
        toolsUseCase.deleteToolHolder(toolHolder)
        cancelDeleteToolHolder()
        loadToolHolders()
    }

    fun onMountTool(toolHolder: ToolHolder) {

    }

    fun loadToolHolder(toolHolder: ToolHolder) {
        coroutineScope.launch {
            toolsUseCase.loadTool(toolHolder.holderNumber)
        }
    }

    fun deleteCuttingInsert(insert: CuttingInsert) {
        toolsUseCase.deleteCuttingInsert(insert)
        cancelDeleteCuttingInsert()
        loadCuttingInserts()
    }

    fun requestDeleteLatheTool(latheTool: LatheTool) {
        mutableState.update {
            it.copy(
                latheToolDeleteModel = LatheToolDeleteModel(latheTool),
            )
        }
    }

    fun cancelDeleteLatheTool() {
        mutableState.update {
            it.copy(
                latheToolDeleteModel = null,
            )
        }
    }

    fun deleteLatheTool(latheTool: LatheTool) {
        toolsUseCase.deleteLatheTool(latheTool)
        cancelDeleteLatheTool()
        loadLatheTools()
    }

    fun requestDeleteCuttingInsert(cuttingInsert: CuttingInsert) {
        mutableState.update {
            it.copy(
                cuttingInsertDeleteModel = CuttingInsertDeleteModel(cuttingInsert),
            )
        }
    }

    fun cancelDeleteCuttingInsert() {
        mutableState.update {
            it.copy(
                cuttingInsertDeleteModel = null,
            )
        }
    }
}