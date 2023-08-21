package com.mindovercnc.linuxcnc.screen.manual.tapersettings

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.mindovercnc.linuxcnc.settings.SettingsRepository
import com.mindovercnc.linuxcnc.settings.model.DoubleKey
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class TaperSettingsScreenModel(
    private val settingsRepository: SettingsRepository,
) : StateScreenModel<TaperSettingsState>(TaperSettingsState()), TaperSettingsComponent {

    init {
        settingsRepository
            .flow(DoubleKey.TaperAngle)
            .onEach { setAngle(it) }
            .launchIn(coroutineScope)
    }

    override fun setAngle(value: Double) {
        mutableState.update {
            it.copy(
                angle = value,
            )
        }
    }

    override fun applyChanges() {
        settingsRepository.apply { put(DoubleKey.TaperAngle, mutableState.value.angle) }
    }
}
