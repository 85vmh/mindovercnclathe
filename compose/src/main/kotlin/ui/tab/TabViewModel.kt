import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.mindovercnc.repository.CncStatusRepository
import kotlinx.coroutines.flow.*
import mu.KotlinLogging
import ro.dragossusi.proto.linuxcnc.isEstop
import ro.dragossusi.proto.linuxcnc.isHomed
import ro.dragossusi.proto.linuxcnc.isOn

data class AppState(val isBottomBarEnabled: Boolean = true, val currentTool: Int = 0)

/** ViewModel for the main navigation. */
class TabViewModel(cncStatusRepository: CncStatusRepository) :
  StateScreenModel<AppState>(AppState()) {

  init {
    cncStatusRepository
      .cncStatusFlow()
      .map {
        val isBottomBarEnabled = it.isEstop.not() && it.isOn && it.isHomed()
        AppState(
          isBottomBarEnabled = isBottomBarEnabled,
          currentTool = it.ioStatus.toolStatus.toolInSpindle
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
