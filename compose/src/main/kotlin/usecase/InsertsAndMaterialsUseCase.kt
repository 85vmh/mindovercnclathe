package usecase

import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.dispatchers.createScope
import com.mindovercnc.linuxcnc.model.*
import com.mindovercnc.model.*
import com.mindovercnc.repository.*
import kotlinx.coroutines.flow.*

class InsertsAndMaterialsUseCase(
  ioDispatcher: IoDispatcher,
  private val cuttingInsertsRepository: CuttingInsertsRepository,
) {

  private val scope = ioDispatcher.createScope()
  fun createCuttingInsert(cuttingInsert: CuttingInsert) {}

  fun updateCuttingInsert(cuttingInsert: CuttingInsert) {}

  fun getCuttingInserts(): Flow<List<CuttingInsert>> {
    return flowOf(cuttingInsertsRepository.findAll())
  }
}
