package com.mindovercnc.linuxcnc.screen.tools.add.cuttinginsert

import com.arkivanov.decompose.ComponentContext
import com.mindovercnc.linuxcnc.domain.tools.CuttingInsertUseCase
import com.mindovercnc.linuxcnc.screen.BaseScreenModel
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert
import com.mindovercnc.linuxcnc.tools.model.MadeOf
import com.mindovercnc.model.InsertClearance
import com.mindovercnc.model.InsertShape
import com.mindovercnc.model.MountingAndChipBreaker
import com.mindovercnc.model.ToleranceClass
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.instance
import org.kodein.di.instanceOrNull

class AddEditCuttingInsertScreenModel(di: DI, componentContext: ComponentContext) :
    BaseScreenModel<AddEditCuttingInsertState>(AddEditCuttingInsertState(), componentContext),
    AddEditCuttingInsertComponent {

    private val cuttingInsertUseCase: CuttingInsertUseCase by di.instance()

    override val editItem: CuttingInsert? by di.instanceOrNull()

    init {
        editItem?.let(::initEdit)
    }

    override val title: String
        get() =
            when (val insert = editItem) {
                null -> "Add Cutting Insert"
                else -> "Edit Cutting Insert #${insert.code}"
            }

    override fun setMadeOf(value: MadeOf) {
        mutableState.update {
            it.copy(
                madeOf = value,
            )
        }
    }

    override fun setInsertShape(value: InsertShape) {
        mutableState.update {
            when {
                value.angle != null -> it.copy(insertShape = value, tipAngle = value.angle!!)
                else -> it.copy(insertShape = value)
            }
        }
    }

    override fun setInsertClearance(value: InsertClearance) {
        mutableState.update { it.copy(insertClearance = value) }
    }

    override fun setToleranceClass(value: ToleranceClass) {
        mutableState.update { it.copy(toleranceClass = value) }
    }

    override fun setMountingAndChipBreaker(value: MountingAndChipBreaker) {
        mutableState.update { it.copy(mountingAndChipBreaker = value) }
    }

    override fun setTipAngle(value: Int) {
        mutableState.update { it.copy(tipAngle = value) }
    }

    override fun setTipRadius(value: Double) {
        mutableState.update { it.copy(tipRadius = value) }
    }

    override fun setSize(value: Double) {
        mutableState.update { it.copy(size = value) }
    }

    override fun reloadFeedsAndSpeeds() {
        mutableState.update { it.copy(feedsAndSpeedsList = DummyData.feedsAndSpeeds) }
    }

    override fun applyChanges() {
        val insert = createCuttingInsert() ?: return
        coroutineScope.launch {
            mutableState.update { it.copy(isLoading = true) }
            when (editItem) {
                null -> cuttingInsertUseCase.createCuttingInsert(insert)
                else -> cuttingInsertUseCase.updateCuttingInsert(insert)
            }
            mutableState.update { it.copy(isLoading = false, isFinished = true) }
        }
    }

    private fun createCuttingInsert(): CuttingInsert? {
        return with(state.value) {
            CuttingInsert(
                id = cuttingInsertId,
                madeOf = madeOf ?: return null,
                code = this.getCodeFromSelection(),
                tipRadius = tipRadius,
                tipAngle = tipAngle.toDouble(),
                size = size
            )
        }
    }

    private fun initEdit(insert: CuttingInsert) {
        mutableState.update {
            it.copy(
                cuttingInsertId = insert.id,
                madeOf = insert.madeOf,
                tipAngle = insert.tipAngle.toInt(),
                tipRadius = insert.tipRadius,
                size = insert.size,
                insertShape = InsertShape.fromCode(insert.code),
                insertClearance = InsertClearance.fromCode(insert.code),
                toleranceClass = ToleranceClass.fromCode(insert.code),
                mountingAndChipBreaker = MountingAndChipBreaker.fromCode(insert.code),
                feedsAndSpeedsList = DummyData.feedsAndSpeeds
            )
        }
    }
}
