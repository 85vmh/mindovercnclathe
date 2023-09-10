package com.mindovercnc.linuxcnc.screen.tools.add.feedspeed

import com.arkivanov.decompose.ComponentContext
import com.mindovercnc.linuxcnc.domain.ToolsUseCase
import com.mindovercnc.linuxcnc.screen.BaseScreenModel
import com.mindovercnc.model.FeedsAndSpeeds
import kotlinx.coroutines.flow.update
import org.kodein.di.DI
import org.kodein.di.instance
import org.kodein.di.instanceOrNull

class AddEditFeedsAndSpeedsScreenModel(di: DI, componentContext: ComponentContext) :
    BaseScreenModel<AddEditFeedsAndSpeedsState>(AddEditFeedsAndSpeedsState(), componentContext),
    AddEditFeedsAndSpeedsComponent {

    override val editItem: FeedsAndSpeeds? by di.instanceOrNull()
    private val toolsUseCase: ToolsUseCase by di.instance()

    override val title: String
        get() =
            when (editItem) {
                null -> "Add Feeds & Speeds"
                else -> "Edit Feeds & Speeds"
            }

    init {
        editItem?.let(::initItem)
    }

    override fun setMinAp(value: Double) {
        mutableState.update { it.copy(minAp = value) }
    }

    override fun setMaxAp(value: Double) {
        mutableState.update { it.copy(maxAp = value) }
    }

    override fun setMinFn(value: Double) {
        mutableState.update { it.copy(minFn = value) }
    }

    override fun setMaxFn(value: Double) {
        mutableState.update { it.copy(maxFn = value) }
    }

    override fun setMinVc(value: Int) {
        mutableState.update { it.copy(minVc = value) }
    }

    override fun setMaxVc(value: Int) {
        mutableState.update { it.copy(maxVc = value) }
    }

    override fun applyChanges(): Boolean {
        // TODO
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
        return true
    }

    private fun initItem(feedsAndSpeeds: FeedsAndSpeeds) {
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
