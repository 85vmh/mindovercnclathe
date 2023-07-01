package ui.screen.tools.root.tabs.toolholder

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.mindovercnc.model.LatheTool
import com.mindovercnc.model.ToolHolder
import com.mindovercnc.model.ToolHolderType
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import usecase.ToolsUseCase

class AddEditToolHolderScreenModel(
    val toolHolder: ToolHolder? = null,
    val toolsUseCase: ToolsUseCase
) : StateScreenModel<AddEditToolHolderScreenModel.State>(State()) {

    data class State(
        val holderNumber: Int? = null,
        val type: ToolHolderType = ToolHolderType.Generic,
        val latheTool: LatheTool? = null,
        val latheToolsList: List<LatheTool> = emptyList(),
        val unmountedLatheTools: List<LatheTool> = emptyList()
    )

    init {
        toolHolder?.let {
            mutableState.update {
                it.copy(
                    holderNumber = toolHolder.holderNumber,
                    type = toolHolder.type,
                    latheTool = toolHolder.latheTool
                )
            }
        }

        toolsUseCase.getLatheTools()
            .onEach { toolsList ->
                mutableState.update {
                    it.copy(
                        latheToolsList = toolsList,
                    )
                }
            }.launchIn(coroutineScope)
    }

    fun setHolderNumber(value: Int) {
        mutableState.update {
            it.copy(
                holderNumber = value,
            )
        }
    }

    fun setHolderType(value: ToolHolderType) {
        mutableState.update {
            it.copy(
                type = value,
            )
        }
    }

    fun setLatheTool(value: LatheTool) {
        mutableState.update {
            it.copy(
                latheTool = value,
            )
        }
    }

    fun applyChanges() {
        with(mutableState.value) {
            val th = ToolHolder(
                holderNumber = this.holderNumber ?: return@with,
                type = this.type,
                latheTool = this.latheTool
            )
            when (toolHolder) {
                null -> toolsUseCase.createToolHolder(th)
                else -> toolsUseCase.updateToolHolder(th)
            }
        }
    }
}