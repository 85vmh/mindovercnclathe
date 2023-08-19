package com.mindovercnc.linuxcnc.screen.manual.turningsettings

import cafe.adriel.voyager.core.model.StateScreenModel
import com.mindovercnc.linuxcnc.settings.model.BooleanKey
import com.mindovercnc.linuxcnc.settings.model.DoubleKey
import com.mindovercnc.linuxcnc.settings.model.IntegerKey
import com.mindovercnc.linuxcnc.settings.SettingsRepository
import kotlinx.coroutines.flow.update

class TurningSettingsScreenModel(
    val settingsRepository: SettingsRepository
) : StateScreenModel<TurningSettingsScreenModel.State>(State()) {

    data class State(
        val rpmValue: Int = 0,
        val cssValue: Int = 0,
        val maxSpeed: Int = 0,
        val orientAngle: Double = 0.0,
        val isOrientActive: Boolean = false,
        val unitsPerRevValue: Double = 0.0,
        val unitsPerMinValue: Double = 0.0,
        val isRpmActive: Boolean = true,
        val isUnitPerRevActive: Boolean = true,
        val cssUnit: String = "m/min",
        val feedUnit: String = "mm",
    )

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

    fun setRpmValue(value: Int) {
        mutableState.update {
            it.copy(rpmValue = value)
        }
    }

    fun setCssValue(value: Int) {
        mutableState.update {
            it.copy(cssValue = value)
        }
    }

    fun setMaxSpeedValue(value: Int) {
        mutableState.update {
            it.copy(maxSpeed = value)
        }
    }

    fun setOrientAngle(value: Double) {
        mutableState.update {
            it.copy(orientAngle = value)
        }
    }

    fun setUnitsPerRev(value: Double) {
        mutableState.update {
            it.copy(unitsPerRevValue = value)
        }
    }

    fun setUnitsPerMin(value: Double) {
        mutableState.update {
            it.copy(unitsPerMinValue = value)
        }
    }

    fun setRpmActive(value: Boolean) {
        mutableState.update {
            it.copy(isRpmActive = value)
        }
    }

    fun setUnitsPerRevActive(value: Boolean) {
        mutableState.update {
            it.copy(isUnitPerRevActive = value)
        }
    }

    fun setOrientActive(value: Boolean) {
        mutableState.update {
            it.copy(isOrientActive = value)
        }
    }

    fun applyChanges() {
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