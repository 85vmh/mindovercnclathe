package com.mindovercnc.linuxcnc.screen.tools.root.tabs.cuttinginsert

import com.mindovercnc.linuxcnc.tools.model.MadeOf
import com.mindovercnc.model.*

data class AddEditCuttingInsertState(
    val cuttingInsertId: Int? = null,
    val madeOf: MadeOf? = null,
    val insertShape: InsertShape? = null,
    val insertClearance: InsertClearance? = null,
    val toleranceClass: ToleranceClass? = null,
    val mountingAndChipBreaker: MountingAndChipBreaker? = null,
    val tipAngle: Int = 0,
    val tipRadius: Double = 0.0,
    val size: Double = 0.0,
    val feedsAndSpeedsList: List<FeedsAndSpeeds> = emptyList(),
) {
    val isCustomGroundTool: Boolean
        get() = madeOf == MadeOf.Hss || madeOf == MadeOf.HssCo

    fun getCodeFromSelection(): String? {
        return if (insertShape != null && insertClearance != null && toleranceClass != null && mountingAndChipBreaker != null) {
            val letter1 = insertShape.name
            val letter2 = insertClearance.name
            val letter3 = toleranceClass.name
            val letter4 = mountingAndChipBreaker.name
            return letter1 + letter2 + letter3 + letter4
        } else null
    }
}