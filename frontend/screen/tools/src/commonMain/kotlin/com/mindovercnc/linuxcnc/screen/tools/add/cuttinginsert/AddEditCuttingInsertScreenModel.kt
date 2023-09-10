package com.mindovercnc.linuxcnc.screen.tools.add.cuttinginsert

import com.arkivanov.decompose.ComponentContext
import com.mindovercnc.linuxcnc.domain.ToolsUseCase
import com.mindovercnc.linuxcnc.screen.BaseScreenModel
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert
import com.mindovercnc.linuxcnc.tools.model.MadeOf
import com.mindovercnc.model.*
import kotlinx.coroutines.flow.update
import org.kodein.di.DI
import org.kodein.di.instance
import org.kodein.di.instanceOrNull

class AddEditCuttingInsertScreenModel(di: DI, componentContext: ComponentContext) :
    BaseScreenModel<AddEditCuttingInsertState>(AddEditCuttingInsertState(), componentContext),
    AddEditCuttingInsertComponent {

    private val toolsUseCase: ToolsUseCase by di.instance()

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

    override fun applyChanges(): Boolean {
        with(mutableState.value) {
            val insert =
                CuttingInsert(
                    id = cuttingInsertId,
                    madeOf = madeOf ?: return false,
                    code = this.getCodeFromSelection(),
                    tipRadius = tipRadius,
                    tipAngle = tipAngle.toDouble(),
                    size = size
                )
            when (editItem) {
                null -> toolsUseCase.createCuttingInsert(insert)
                else -> toolsUseCase.updateCuttingInsert(insert)
            }
        }
        // TODO: add validation
        return true
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
                feedsAndSpeedsList = dummyFeedsAndSpeeds
            )
        }
    }

    companion object {

        private val dummyFeedsAndSpeeds =
            listOf(
                FeedsAndSpeeds(
                    "Steel",
                    MaterialCategory.P,
                    0.2f..2.0f,
                    0.1f..0.3f,
                    100..200,
                ),
                FeedsAndSpeeds(
                    "Delrin",
                    MaterialCategory.N,
                    0.2f..2.0f,
                    0.1f..0.3f,
                    100..200,
                ),
                FeedsAndSpeeds(
                    "Aluminium",
                    MaterialCategory.N,
                    0.2f..2.0f,
                    0.1f..0.3f,
                    100..200,
                ),
                FeedsAndSpeeds(
                    "Cast Iron",
                    MaterialCategory.K,
                    0.2f..2.0f,
                    0.1f..0.3f,
                    100..200,
                ),
                FeedsAndSpeeds(
                    "304 Stainless",
                    MaterialCategory.M,
                    0.2f..2.0f,
                    0.1f..0.3f,
                    100..200
                ),
                FeedsAndSpeeds(
                    "306 Stainless",
                    MaterialCategory.M,
                    0.2f..2.0f,
                    0.1f..0.3f,
                    100..200
                ),
            )
    }
}
