package com.mindovercnc.linuxcnc.screen.tools.root.tabs.cuttinginsert.add.feedsandspeeds

import com.mindovercnc.model.MaterialCategory

data class AddEditFeedsAndSpeedsState(
    val materialName: String = "",
    val materialCategory: MaterialCategory? = null,
    val minAp: Double = 0.0,
    val maxAp: Double = 0.0,
    val minFn: Double = 0.0,
    val maxFn: Double = 0.0,
    val minVc: Int = 0,
    val maxVc: Int = 0
)