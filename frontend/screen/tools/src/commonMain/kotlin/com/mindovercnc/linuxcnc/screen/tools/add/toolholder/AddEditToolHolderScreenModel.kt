package com.mindovercnc.linuxcnc.screen.tools.add.toolholder

import com.arkivanov.decompose.ComponentContext
import com.mindovercnc.linuxcnc.domain.ToolsUseCase
import com.mindovercnc.linuxcnc.screen.BaseScreenModel
import com.mindovercnc.linuxcnc.tools.model.LatheTool
import com.mindovercnc.linuxcnc.tools.model.ToolHolder
import com.mindovercnc.linuxcnc.tools.model.ToolHolderType
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import mu.KotlinLogging
import org.kodein.di.DI
import org.kodein.di.instance
import org.kodein.di.instanceOrNull

class AddEditToolHolderScreenModel(di: DI, componentContext: ComponentContext) :
    BaseScreenModel<AddEditToolHolderState>(AddEditToolHolderState(), componentContext),
    AddEditToolHolderComponent {

    private val toolsUseCase: ToolsUseCase by di.instance()
    override val editItem: ToolHolder? by di.instanceOrNull()

    override val title: String
        get() =
            when (val toolHolder = editItem) {
                null -> "Add Tool Holder"
                else -> "Edit Tool Holder #${toolHolder.holderNumber}"
            }

    init {
        editItem?.let { holder -> initEdit(holder) }

        toolsUseCase
            .getLatheTools()
            .onEach { toolsList -> mutableState.update { it.copy(latheToolsList = toolsList) } }
            .launchIn(coroutineScope)

        setHolderType(ToolHolderType.Generic)
    }

    override fun setHolderNumber(value: Int) {
        mutableState.update { it.copy(holderNumber = value) }
    }

    override fun setHolderType(value: ToolHolderType) {
        toolsUseCase
            .getUnmountedLatheTools(value)
            .onEach { toolsList ->
                LOG.debug { "holderType $value, toolsList: $toolsList" }
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

    override fun applyChanges(): Boolean {
        with(mutableState.value) {
            val th =
                ToolHolder(
                    holderNumber = this.holderNumber ?: return@with,
                    type = this.type,
                    latheTool = this.latheTool
                )
            when (editItem) {
                null -> toolsUseCase.createToolHolder(th)
                else -> toolsUseCase.updateToolHolder(th)
            }
        }
        // TODO: add validation
        return true
    }

    private fun initEdit(holder: ToolHolder) {
        mutableState.update {
            it.copy(
                holderNumber = holder.holderNumber,
                type = holder.type,
                latheTool = holder.latheTool
            )
        }
    }

    companion object {
        private val LOG = KotlinLogging.logger("AddEditToolHolderScreenModel")
    }
}
