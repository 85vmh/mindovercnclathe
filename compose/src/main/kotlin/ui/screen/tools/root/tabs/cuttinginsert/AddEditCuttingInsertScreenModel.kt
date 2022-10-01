package ui.screen.tools.root.tabs.cuttinginsert

import cafe.adriel.voyager.core.model.StateScreenModel
import com.mindovercnc.model.*
import kotlinx.coroutines.flow.update
import usecase.ToolsUseCase

class AddEditCuttingInsertScreenModel(
    val cuttingInsert: CuttingInsert? = null,
    val toolsUseCase: ToolsUseCase
) : StateScreenModel<AddEditCuttingInsertScreenModel.State>(State()) {

    data class State(
        val cuttingInsertId: Int? = null,
        val madeOfList: List<MadeOf> = MadeOf.values().toList(),
        val madeOf: MadeOf? = null,
        val insertShapes: List<InsertShape> = InsertShape.values().toList(),
        val insertShape: InsertShape? = null,
        val insertClearances: List<InsertClearance> = InsertClearance.values().toList(),
        val insertClearance: InsertClearance? = null,
        val toleranceClasses: List<ToleranceClass> = ToleranceClass.values().toList(),
        val toleranceClass: ToleranceClass? = null,
        val mountingAndChipBreakerLists: List<MountingAndChipBreaker> = MountingAndChipBreaker.values().toList(),
        val mountingAndChipBreaker: MountingAndChipBreaker? = null,
        val tipAngle: Int = 0,
        val tipRadius: Double = 0.0,
        val size: Double = 0.0,
    ) {
        val isCustomGroundTool: Boolean
            get() = madeOf == MadeOf.Hss || madeOf == MadeOf.HssCo
    }

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
                    mountingAndChipBreaker = MountingAndChipBreaker.fromCode(insert.code)
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

    private fun getCodeFromSelection(state: State): String? {
        return if (state.insertShape != null && state.insertClearance != null && state.toleranceClass != null && state.mountingAndChipBreaker != null) {
            val letter1 = state.insertShape.name
            val letter2 = state.insertClearance.name
            val letter3 = state.toleranceClass.name
            val letter4 = state.mountingAndChipBreaker.name
            return letter1 + letter2 + letter3 + letter4
        } else null
    }

    fun applyChanges() {
        with(mutableState.value) {
            val insert = CuttingInsert(
                id = cuttingInsertId,
                madeOf = madeOf!!,
                code = getCodeFromSelection(this),
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