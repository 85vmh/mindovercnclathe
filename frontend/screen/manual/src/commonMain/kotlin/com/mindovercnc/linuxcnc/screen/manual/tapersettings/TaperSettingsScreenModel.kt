package com.mindovercnc.linuxcnc.screen.manual.tapersettings

import com.arkivanov.decompose.ComponentContext
import com.mindovercnc.linuxcnc.screen.BaseScreenModel
import com.mindovercnc.linuxcnc.settings.SettingsRepository
import com.mindovercnc.linuxcnc.settings.model.DoubleKey
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import org.kodein.di.DI
import org.kodein.di.instance

class TaperSettingsScreenModel(
    di: DI,
    componentContext: ComponentContext,
) :
    BaseScreenModel<TaperSettingsState>(TaperSettingsState(), componentContext),
    TaperSettingsComponent {
    private val settingsRepository: SettingsRepository by di.instance()

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
