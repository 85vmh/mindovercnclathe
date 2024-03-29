package com.mindovercnc.linuxcnc.screen.tools.list.tabs.cuttinginsert

import com.arkivanov.decompose.ComponentContext
import com.mindovercnc.linuxcnc.domain.tools.CuttingInsertUseCase
import com.mindovercnc.linuxcnc.screen.BaseScreenModel
import com.mindovercnc.linuxcnc.screen.tools.list.ui.CuttingInsertDeleteModel
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.instance

class CuttingInsertScreenModel(di: DI, componentContext: ComponentContext) :
    BaseScreenModel<CuttingInsertState>(CuttingInsertState(), componentContext),
    CuttingInsertComponent {

    private val cuttingInsertUseCase: CuttingInsertUseCase by di.instance()

    init {
        loadCuttingInserts()
    }

    override fun loadCuttingInserts() {
        coroutineScope.launch {
            val insertsList = cuttingInsertUseCase.getCuttingInserts()
            mutableState.update { it.copy(cuttingInserts = insertsList) }
        }
    }

    override fun deleteCuttingInsert(insert: CuttingInsert) {
        coroutineScope.launch {
            cuttingInsertUseCase.deleteCuttingInsert(insert)
            cancelDeleteCuttingInsert()
            loadCuttingInserts()
        }
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
