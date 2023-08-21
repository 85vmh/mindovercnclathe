package com.mindovercnc.linuxcnc.screen.manual.turningsettings

import cafe.adriel.voyager.core.model.StateScreenModel
import com.mindovercnc.linuxcnc.settings.SettingsRepository
import com.mindovercnc.linuxcnc.settings.model.BooleanKey
import com.mindovercnc.linuxcnc.settings.model.DoubleKey
import com.mindovercnc.linuxcnc.settings.model.IntegerKey
import kotlinx.coroutines.flow.update

class TurningSettingsScreenModel(
    private val settingsRepository: SettingsRepository,
) : StateScreenModel<TurningSettingsState>(TurningSettingsState()), TurningSettingsComponent {

    init {
        mutableState.update {
            it.copy(
                rpmValue = settingsRepository.get(IntegerKey.RpmValue, 300),
                cssValue = settingsRepository.get(IntegerKey.CssValue, 200),
                maxSpeed = settingsRepository.get(IntegerKey.MaxCssRpm, 1500),
                isOrientActive = settingsRepository.get(BooleanKey.OrientedStop),
                orientAngle = settingsRepository.get(DoubleKey.OrientAngle),
                unitsPerRevValue = settingsRepository.get(DoubleKey.FeedPerRev, 0.1),
                unitsPerMinValue = settingsRepository.get(DoubleKey.FeedPerMin, 50.0),
                isRpmActive = settingsRepository.get(BooleanKey.IsRpmMode),
                isUnitPerRevActive = settingsRepository.get(BooleanKey.IsUnitsPerRevMode)
            )
        }
    }

    override fun setRpmValue(value: Int) {
        mutableState.update { it.copy(rpmValue = value) }
    }

    override fun setCssValue(value: Int) {
        mutableState.update { it.copy(cssValue = value) }
    }

    override fun setMaxSpeedValue(value: Int) {
        mutableState.update { it.copy(maxSpeed = value) }
    }

    override fun setOrientAngle(value: Double) {
        mutableState.update { it.copy(orientAngle = value) }
    }

    override fun setUnitsPerRev(value: Double) {
        mutableState.update { it.copy(unitsPerRevValue = value) }
    }

    override fun setUnitsPerMin(value: Double) {
        mutableState.update { it.copy(unitsPerMinValue = value) }
    }

    override fun setRpmActive(value: Boolean) {
        mutableState.update { it.copy(isRpmActive = value) }
    }

    override fun setUnitsPerRevActive(value: Boolean) {
        mutableState.update { it.copy(isUnitPerRevActive = value) }
    }

    override fun setOrientActive(value: Boolean) {
        mutableState.update { it.copy(isOrientActive = value) }
    }

    override fun applyChanges() {
        val currentState = mutableState.value
        settingsRepository.apply {
            put(BooleanKey.IsRpmMode, currentState.isRpmActive)
            put(IntegerKey.RpmValue, currentState.rpmValue)
            put(IntegerKey.CssValue, currentState.cssValue)
            put(IntegerKey.MaxCssRpm, currentState.maxSpeed)
            put(BooleanKey.OrientedStop, currentState.isOrientActive)
            put(DoubleKey.OrientAngle, currentState.orientAngle)
            put(BooleanKey.IsUnitsPerRevMode, currentState.isUnitPerRevActive)
            put(DoubleKey.FeedPerRev, currentState.unitsPerRevValue)
            put(DoubleKey.FeedPerMin, currentState.unitsPerMinValue)
        }
    }
}
