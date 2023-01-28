import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.mindovercnc.repository.IoStatusRepository
import kotlinx.coroutines.flow.*
import usecase.MachineUsableUseCase

data class AppState(val isBottomBarEnabled: Boolean = true, val currentTool: Int = 0)

/** ViewModel for the main navigation. */
class TabViewModel(
  ioStatusRepository: IoStatusRepository,
  machineUsableUseCase: MachineUsableUseCase
) : StateScreenModel<AppState>(AppState()) {

  init {
    machineUsableUseCase.machineUsableFlow
      .onEach { usable -> mutableState.update { it.copy(isBottomBarEnabled = usable) } }
      .launchIn(coroutineScope)

    ioStatusRepository.ioStatusFlow
      .map { it.toolStatus.toolInSpindle }
      .onEach { tool -> mutableState.update { it.copy(currentTool = tool) } }
      .launchIn(coroutineScope)
  }
}
