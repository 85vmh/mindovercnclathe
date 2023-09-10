package com.mindovercnc.linuxcnc.screen.tools.root.tabs.cuttinginsert.add.feedsandspeeds

import cafe.adriel.voyager.core.model.StateScreenModel
import com.mindovercnc.linuxcnc.domain.ToolsUseCase
import com.mindovercnc.model.FeedsAndSpeeds
import kotlinx.coroutines.flow.update

class AddEditFeedsAndSpeedsScreenModel(
    initialFeedsAndSpeeds: FeedsAndSpeeds?,
    val toolsUseCase: ToolsUseCase
) :
    StateScreenModel<AddEditFeedsAndSpeedsState>(AddEditFeedsAndSpeedsState()),
    AddEditFeedsAndSpeedsComponent {

    init {
        initialFeedsAndSpeeds?.let { feedsAndSpeeds ->
            mutableState.update {
                it.copy(
                    materialName = feedsAndSpeeds.materialName,
                    materialCategory = feedsAndSpeeds.materialCategory,
                    minAp = feedsAndSpeeds.ap.start.toDouble(),
                    maxAp = feedsAndSpeeds.ap.endInclusive.toDouble(),
                    minFn = feedsAndSpeeds.fn.start.toDouble(),
                    maxFn = feedsAndSpeeds.fn.endInclusive.toDouble(),
                    minVc = feedsAndSpeeds.vc.first,
                    maxVc = feedsAndSpeeds.vc.last
                )
            }
        }
    }

    override fun setMinAp(value: Double) {
        mutableState.update {
            it.copy(
                minAp = value
            )
        }
    }

    override fun setMaxAp(value: Double) {
        mutableState.update {
            it.copy(
                maxAp = value
            )
        }
    }

    override fun setMinFn(value: Double) {
        mutableState.update {
            it.copy(
                minFn = value
            )
        }
    }

    override fun setMaxFn(value: Double) {
        mutableState.update {
            it.copy(
                maxFn = value
            )
        }
    }

    override fun setMinVc(value: Int) {
        mutableState.update {
            it.copy(
                minVc = value
            )
        }
    }

    override fun setMaxVc(value: Int) {
        mutableState.update {
            it.copy(
                maxVc = value
            )
        }
    }

    override fun applyChanges() {
        with(mutableState.value) {
//            val insert =
//                CuttingInsert(
//                    id = cuttingInsertId,
//                    madeOf = madeOf!!,
//                    code = this.getCodeFromSelection(),
//                    tipRadius = tipRadius,
//                    tipAngle = tipAngle.toDouble(),
//                    size = size
//                )
//            when (cuttingInsert) {
//                null -> toolsUseCase.createCuttingInsert(insert)
//                else -> toolsUseCase.updateCuttingInsert(insert)
//            }
        }
    }
}