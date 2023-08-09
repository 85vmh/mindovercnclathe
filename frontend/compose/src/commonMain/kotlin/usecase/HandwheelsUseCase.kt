package usecase

import com.mindovercnc.data.linuxcnc.HalRepository
import com.mindovercnc.repository.TaskStatusRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import ro.dragossusi.proto.linuxcnc.status.TaskMode
import ui.screen.manual.root.HandWheelsUiModel

class HandWheelsUseCase(
    taskStatusRepository: TaskStatusRepository,
    halRepository: HalRepository,
) {

    @OptIn(FlowPreview::class)
    val handWheelsUiModel =
        combine(
            taskStatusRepository.taskStatusFlow.map { it.taskMode == TaskMode.TaskModeManual },
            halRepository.jogIncrementValue().debounce(200L)
        ) { isManualMode, jogIncrement ->
            HandWheelsUiModel(isManualMode, jogIncrement)
        }
}
