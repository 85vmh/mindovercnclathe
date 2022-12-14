import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.mindovercnc.model.isEstop
import com.mindovercnc.model.isHomed
import com.mindovercnc.model.isOn
import com.mindovercnc.repository.CncStatusRepository
import kotlinx.coroutines.flow.*

data class AppState(
    val isBottomBarEnabled: Boolean = true,
    val currentTool: Int = 0
)

class TabViewModel(
    cncStatusRepository: CncStatusRepository
) : StateScreenModel<AppState>(AppState()) {

    init {
        cncStatusRepository.cncStatusFlow()
            .map {
                AppState(
                    isBottomBarEnabled = it.isEstop.not() && it.isOn && it.isHomed(),
                    currentTool = it.ioStatus.toolStatus.currentLoadedTool
                )
            }
            .distinctUntilChanged()
            .onEach { newAppState ->
                mutableState.update {
                    it.copy(
                        isBottomBarEnabled = newAppState.isBottomBarEnabled,
                        currentTool = newAppState.currentTool
                    )
                }
            }
            .launchIn(coroutineScope)
    }
}