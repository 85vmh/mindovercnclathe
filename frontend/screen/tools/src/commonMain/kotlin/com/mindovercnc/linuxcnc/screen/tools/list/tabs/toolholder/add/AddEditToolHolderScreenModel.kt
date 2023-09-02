package com.mindovercnc.linuxcnc.screen.tools.list.tabs.toolholder.add

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.mindovercnc.linuxcnc.domain.ToolsUseCase
import com.mindovercnc.linuxcnc.tools.model.LatheTool
import com.mindovercnc.linuxcnc.tools.model.ToolHolder
import com.mindovercnc.linuxcnc.tools.model.ToolHolderType
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class AddEditToolHolderScreenModel(
    val toolHolder: ToolHolder? = null,
    val toolsUseCase: ToolsUseCase
) : StateScreenModel<AddEditToolHolderState>(AddEditToolHolderState()), AddEditToolHolderComponent {

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

        toolsUseCase
            .getLatheTools()
            .onEach { toolsList ->
                mutableState.update {
                    it.copy(
                        latheToolsList = toolsList,
                    )
                }
            }
            .launchIn(coroutineScope)
        setHolderType(ToolHolderType.Generic)
    }

    override fun setHolderNumber(value: Int) {
        mutableState.update {
            it.copy(
                holderNumber = value,
            )
        }
    }

    override fun setHolderType(value: ToolHolderType) {
        toolsUseCase
            .getUnmountedLatheTools(value)
            .onEach { toolsList ->
                println("holderType $value, toolsList: $toolsList")
                mutableState.update {
                    it.copy(
                        type = value,
                        unmountedLatheTools = toolsList,
                    )
                }
            }
            .launchIn(coroutineScope)
    }

    override fun setLatheTool(value: LatheTool) {
        mutableState.update {
            it.copy(
                latheTool = value,
            )
        }
    }

    override fun applyChanges() {
        with(mutableState.value) {
            val th =
                ToolHolder(
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
