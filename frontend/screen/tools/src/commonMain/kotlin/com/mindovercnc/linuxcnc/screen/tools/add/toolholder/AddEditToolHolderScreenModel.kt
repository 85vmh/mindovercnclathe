package com.mindovercnc.linuxcnc.screen.tools.add.toolholder

import com.arkivanov.decompose.ComponentContext
import com.mindovercnc.linuxcnc.domain.tools.LatheToolUseCase
import com.mindovercnc.linuxcnc.domain.tools.ToolHolderUseCase
import com.mindovercnc.linuxcnc.screen.BaseScreenModel
import com.mindovercnc.linuxcnc.tools.model.LatheTool
import com.mindovercnc.linuxcnc.tools.model.ToolHolder
import com.mindovercnc.linuxcnc.tools.model.ToolHolderType
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mu.KotlinLogging
import org.kodein.di.DI
import org.kodein.di.instance
import org.kodein.di.instanceOrNull

class AddEditToolHolderScreenModel(di: DI, componentContext: ComponentContext) :
    BaseScreenModel<AddEditToolHolderState>(AddEditToolHolderState(), componentContext),
    AddEditToolHolderComponent {

    private val latheToolUseCase: LatheToolUseCase by di.instance()
    private val toolHolderUseCase: ToolHolderUseCase by di.instance()
    override val editItem: ToolHolder? by di.instanceOrNull()

    override val title: String
        get() =
            when (val toolHolder = editItem) {
                null -> "Add Tool Holder"
                else -> "Edit Tool Holder #${toolHolder.holderNumber}"
            }

    init {
        editItem?.let { holder -> initEdit(holder) }

        loadLatheTools()

        setHolderType(ToolHolderType.Generic)
    }

    private fun loadLatheTools() {
        coroutineScope.launch {
            val toolsList = latheToolUseCase.getLatheTools()
            mutableState.update { it.copy(latheToolsList = toolsList) }
        }
    }

    override fun setHolderNumber(value: Int) {
        mutableState.update { it.copy(holderNumber = value) }
    }

    override fun setHolderType(value: ToolHolderType) {
        coroutineScope.launch {
            val toolsList = toolHolderUseCase.getUnmountedLatheTools(value)

            LOG.debug { "holderType $value, toolsList: $toolsList" }
            mutableState.update {
                it.copy(
                    type = value,
                    unmountedLatheTools = toolsList,
                )
            }
        }
    }

    override fun setLatheTool(value: LatheTool) {
        mutableState.update {
            it.copy(
                latheTool = value,
            )
        }
    }

    override fun applyChanges() {
        val toolHolder = createToolHolder() ?: return

        coroutineScope.launch {
            mutableState.update { it.copy(isLoading = true) }
            when (editItem) {
                null -> toolHolderUseCase.createToolHolder(toolHolder)
                else -> toolHolderUseCase.updateToolHolder(toolHolder)
            }
            mutableState.update { it.copy(isLoading = false, isFinished = true) }
        }
    }

    private fun createToolHolder(): ToolHolder? {
        return with(state.value) {
            ToolHolder(
                holderNumber = holderNumber ?: return null,
                type = type,
                latheTool = latheTool,
            )
        }
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
