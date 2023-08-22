package com.mindovercnc.linuxcnc.screen.tools.root.tabs.cuttinginsert

import com.arkivanov.decompose.ComponentContext
import com.mindovercnc.linuxcnc.domain.ToolsUseCase
import com.mindovercnc.linuxcnc.screen.BaseScreenModel
import com.mindovercnc.linuxcnc.screen.tools.root.ui.CuttingInsertDeleteModel
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class CuttingInsertScreenModel(
    private val toolsUseCase: ToolsUseCase,
    componentContext: ComponentContext
) :
    BaseScreenModel<CuttingInsertState>(CuttingInsertState(), componentContext),
    CuttingInsertComponent {

    init {
        loadCuttingInserts()
    }

    override fun loadCuttingInserts() {
        toolsUseCase
            .getCuttingInserts()
            .onEach { insertsList -> mutableState.update { it.copy(cuttingInserts = insertsList) } }
            .launchIn(coroutineScope)
    }

    override fun deleteCuttingInsert(insert: CuttingInsert) {
        toolsUseCase.deleteCuttingInsert(insert)
        cancelDeleteCuttingInsert()
        loadCuttingInserts()
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
