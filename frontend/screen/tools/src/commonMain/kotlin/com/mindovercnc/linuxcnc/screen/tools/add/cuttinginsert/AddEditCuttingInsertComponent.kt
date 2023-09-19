package com.mindovercnc.linuxcnc.screen.tools.add.cuttinginsert

import com.mindovercnc.linuxcnc.screen.AppScreenComponent
import com.mindovercnc.linuxcnc.screen.tools.add.AddEditItemComponent
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert
import com.mindovercnc.linuxcnc.tools.model.MadeOf
import com.mindovercnc.model.InsertClearance
import com.mindovercnc.model.InsertShape
import com.mindovercnc.model.MountingAndChipBreaker
import com.mindovercnc.model.ToleranceClass

interface AddEditCuttingInsertComponent :
    AppScreenComponent<AddEditCuttingInsertState>, AddEditItemComponent<CuttingInsert> {
    fun setMadeOf(value: MadeOf)
    fun setInsertShape(value: InsertShape)
    fun setInsertClearance(value: InsertClearance)
    fun setToleranceClass(value: ToleranceClass)
    fun setMountingAndChipBreaker(value: MountingAndChipBreaker)
    fun setTipAngle(value: Int)
    fun setTipRadius(value: Double)
    fun setSize(value: Double)
    fun reloadFeedsAndSpeeds()
}
