package com.mindovercnc.linuxcnc.screen.tools.add.feedspeed

import com.mindovercnc.linuxcnc.screen.AppScreenComponent
import com.mindovercnc.linuxcnc.screen.tools.add.AddEditItemComponent
import com.mindovercnc.model.FeedsAndSpeeds

interface AddEditFeedsAndSpeedsComponent :
    AppScreenComponent<AddEditFeedsAndSpeedsState>, AddEditItemComponent<FeedsAndSpeeds> {

    fun setMinAp(value: Double)
    fun setMaxAp(value: Double)
    fun setMinFn(value: Double)
    fun setMaxFn(value: Double)
    fun setMinVc(value: Int)
    fun setMaxVc(value: Int)
}
