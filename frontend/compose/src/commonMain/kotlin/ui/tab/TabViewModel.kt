import com.mindovercnc.linuxcnc.screen.root.RootState
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.mindovercnc.linuxcnc.domain.MachineUsableUseCase
import com.mindovercnc.repository.IoStatusRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

/** ViewModel for the main navigation. */
class TabViewModel(
    ioStatusRepository: IoStatusRepository,
    machineUsableUseCase: MachineUsableUseCase
) : StateScreenModel<RootState>(RootState()) {

    init {
        machineUsableUseCase.machineUsableFlow
            .onEach { usable -> mutableState.update { it.copy(isBottomBarEnabled = usable) } }
            .launchIn(coroutineScope)

        ioStatusRepository.ioStatusFlow
            .map { it.tool_status!!.tool_in_spindle }
            .onEach { tool -> mutableState.update { it.copy(currentTool = tool) } }
            .launchIn(coroutineScope)
    }
}
