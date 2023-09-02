package com.mindovercnc.linuxcnc.screen.tools.list.tabs.cuttinginsert

import com.mindovercnc.linuxcnc.screen.tools.list.ui.CuttingInsertDeleteModel
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert

data class CuttingInsertState(
    val cuttingInserts: List<CuttingInsert> = emptyList(),
    val cuttingInsertDeleteModel: CuttingInsertDeleteModel? = null,
)
