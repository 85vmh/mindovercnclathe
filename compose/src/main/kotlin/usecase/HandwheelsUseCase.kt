package usecase

import com.mindovercnc.repository.CncStatusRepository
import com.mindovercnc.repository.HalRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import ro.dragossusi.proto.linuxcnc.isInManualMode
import ui.screen.manual.root.HandWheelsUiModel

class HandWheelsUseCase(
    statusRepository: CncStatusRepository,
    halRepository: HalRepository,
) {

    @OptIn(FlowPreview::class)
    val handWheelsUiModel = combine(
        statusRepository.cncStatusFlow.map { it.isInManualMode },
        halRepository.jogIncrementValue().debounce(200L)
    ) { isManualMode, jogIncrement -> HandWheelsUiModel(isManualMode, jogIncrement) }
}