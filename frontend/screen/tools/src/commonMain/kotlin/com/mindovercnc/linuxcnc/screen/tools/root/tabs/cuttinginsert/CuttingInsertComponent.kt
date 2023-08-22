package com.mindovercnc.linuxcnc.screen.tools.root.tabs.cuttinginsert

import com.mindovercnc.linuxcnc.screen.AppScreenComponent
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert

interface CuttingInsertComponent : AppScreenComponent<CuttingInsertState> {
    fun loadCuttingInserts()
    fun deleteCuttingInsert(insert: CuttingInsert)
    fun requestDeleteCuttingInsert(cuttingInsert: CuttingInsert)
    fun cancelDeleteCuttingInsert()
}
