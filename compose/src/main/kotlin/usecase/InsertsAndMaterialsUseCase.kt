package usecase

import com.mindovercnc.repository.*
import com.mindovercnc.linuxcnc.model.*
import com.mindovercnc.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import screen.uimodel.AllowedSpindleDirection
import screen.uimodel.ToolType
import usecase.model.AddEditToolState

class InsertsAndMaterialsUseCase(
    scope: CoroutineScope,
    private val cuttingInsertsRepository: CuttingInsertsRepository,
) {
    fun createCuttingInsert(cuttingInsert: CuttingInsert) {

    }

    fun updateCuttingInsert(cuttingInsert: CuttingInsert) {

    }

    fun getCuttingInserts(): Flow<List<CuttingInsert>> {
        return flowOf(cuttingInsertsRepository.findAll())
    }
}