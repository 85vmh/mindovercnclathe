package ui.screen.manual.tapersettings

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.mindovercnc.repository.SettingsRepository
import com.mindovercnc.model.DoubleKey
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class TaperSettingsScreenModel(
    val settingsRepository: SettingsRepository
) : StateScreenModel<TaperSettingsScreenModel.State>(State()) {

    data class State(
        val angle: Double = 0.0
    )

    init {
        settingsRepository.flow(DoubleKey.TaperAngle)
            .onEach {
                setAngle(it)
            }.launchIn(coroutineScope)
    }

    fun setAngle(value: Double) {
        mutableState.update {
            it.copy(
                angle = value,
            )
        }
    }

    fun applyChanges() {
        settingsRepository.apply {
            put(DoubleKey.TaperAngle, mutableState.value.angle)
        }
    }
}