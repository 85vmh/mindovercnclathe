package com.mindovercnc.linuxcnc.screen.tools.add.toolholder

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.arkivanov.decompose.ComponentContext
import com.mindovercnc.linuxcnc.domain.ToolsUseCase
import com.mindovercnc.linuxcnc.tools.model.LatheTool
import com.mindovercnc.linuxcnc.tools.model.ToolHolder
import com.mindovercnc.linuxcnc.tools.model.ToolHolderType
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import org.kodein.di.DI
import org.kodein.di.instance
import org.kodein.di.instanceOrNull

class AddEditToolHolderScreenModel(
    di: DI,
    componentContext: ComponentContext
) : StateScreenModel<AddEditToolHolderState>(AddEditToolHolderState()), AddEditToolHolderComponent {

    private val toolsUseCase: ToolsUseCase by di.instance()
    val toolHolder: ToolHolder? by di.instanceOrNull()

    init {
        toolHolder?.let { holder->
            mutableState.update {
                it.copy(
                    holderNumber = holder.holderNumber,
                    type = holder.type,
                    latheTool = holder.latheTool
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
