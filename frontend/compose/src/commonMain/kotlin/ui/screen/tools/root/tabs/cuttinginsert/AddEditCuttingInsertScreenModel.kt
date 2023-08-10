package ui.screen.tools.root.tabs.cuttinginsert

import cafe.adriel.voyager.core.model.StateScreenModel
import com.mindovercnc.model.*
import kotlinx.coroutines.flow.update
import usecase.ToolsUseCase

class AddEditCuttingInsertScreenModel(
    val cuttingInsert: CuttingInsert? = null,
    val toolsUseCase: ToolsUseCase
) : StateScreenModel<AddEditCuttingInsertState>(AddEditCuttingInsertState()) {

    val dummyFeedsAndSpeeds = listOf(
        FeedsAndSpeeds("Steel", MaterialCategory.P, 0.2f..2.0f, 0.1f..0.3f, 100..200),
        FeedsAndSpeeds("Delrin", MaterialCategory.N, 0.2f..2.0f, 0.1f..0.3f, 100..200),
        FeedsAndSpeeds("Aluminium", MaterialCategory.N, 0.2f..2.0f, 0.1f..0.3f, 100..200),
        FeedsAndSpeeds("Cast Iron", MaterialCategory.K, 0.2f..2.0f, 0.1f..0.3f, 100..200),
        FeedsAndSpeeds("304 Stainless", MaterialCategory.M, 0.2f..2.0f, 0.1f..0.3f, 100..200),
        FeedsAndSpeeds("306 Stainless", MaterialCategory.M, 0.2f..2.0f, 0.1f..0.3f, 100..200),
    )

    init {
        cuttingInsert?.let { insert ->
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
    }

    fun setMadeOf(value: MadeOf) {
        mutableState.update {
            it.copy(
                madeOf = value,
            )
        }
    }

    fun setInsertShape(value: InsertShape) {
        mutableState.update {
            when {
                value.angle != null -> it.copy(
                    insertShape = value,
                    tipAngle = value.angle!!
                )

                else -> it.copy(
                    insertShape = value,
                )
            }
        }
    }

    fun setInsertClearance(value: InsertClearance) {
        mutableState.update {
            it.copy(
                insertClearance = value,
            )
        }
    }

    fun setToleranceClass(value: ToleranceClass) {
        mutableState.update {
            it.copy(
                toleranceClass = value,
            )
        }
    }

    fun setMountingAndChipBreaker(value: MountingAndChipBreaker) {
        mutableState.update {
            it.copy(
                mountingAndChipBreaker = value,
            )
        }
    }

    fun setTipAngle(value: Int) {
        mutableState.update {
            it.copy(
                tipAngle = value,
            )
        }
    }

    fun setTipRadius(value: Double) {
        mutableState.update {
            it.copy(
                tipRadius = value,
            )
        }
    }

    fun setSize(value: Double) {
        mutableState.update {
            it.copy(
                size = value,
            )
        }
    }

    fun applyChanges() {
        with(mutableState.value) {
            val insert = CuttingInsert(
                id = cuttingInsertId,
                madeOf = madeOf!!,
                code = this.getCodeFromSelection(),
                tipRadius = tipRadius,
                tipAngle = tipAngle.toDouble(),
                size = size
            )
            when (cuttingInsert) {
                null -> toolsUseCase.createCuttingInsert(insert)
                else -> toolsUseCase.updateCuttingInsert(insert)
            }
        }
    }
}