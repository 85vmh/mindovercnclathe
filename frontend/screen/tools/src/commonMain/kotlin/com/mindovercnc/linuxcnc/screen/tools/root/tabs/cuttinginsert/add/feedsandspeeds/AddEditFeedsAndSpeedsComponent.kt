package com.mindovercnc.linuxcnc.screen.tools.root.tabs.cuttinginsert.add.feedsandspeeds

import com.mindovercnc.linuxcnc.screen.AppScreenComponent

interface AddEditFeedsAndSpeedsComponent : AppScreenComponent<AddEditFeedsAndSpeedsState> {

    fun setMinAp(value: Double)
    fun setMaxAp(value: Double)
    fun setMinFn(value: Double)
    fun setMaxFn(value: Double)
    fun setMinVc(value: Int)
    fun setMaxVc(value: Int)

    fun applyChanges()
}