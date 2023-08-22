package com.mindovercnc.linuxcnc.screen.tools.root.tabs.cuttinginsert

import com.mindovercnc.linuxcnc.screen.tools.root.ui.CuttingInsertDeleteModel
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert

data class CuttingInsertState(
    val cuttingInserts: List<CuttingInsert> = emptyList(),
    val cuttingInsertDeleteModel: CuttingInsertDeleteModel? = null,
)
