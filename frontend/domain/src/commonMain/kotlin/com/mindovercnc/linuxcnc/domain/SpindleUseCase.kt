package com.mindovercnc.linuxcnc.domain

import com.mindovercnc.data.lathehal.HalRepository
import com.mindovercnc.data.linuxcnc.CncStatusRepository
import com.mindovercnc.linuxcnc.settings.SettingsRepository
import com.mindovercnc.linuxcnc.settings.model.BooleanKey
import com.mindovercnc.linuxcnc.settings.model.DoubleKey
import com.mindovercnc.linuxcnc.settings.model.IntegerKey
import com.mindovercnc.model.SpindleUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class SpindleUseCase(
    private val statusRepository: CncStatusRepository,
    private val halRepository: HalRepository,
    private val settingsRepository: SettingsRepository
) {

    fun spindleFlow(): Flow<SpindleUiModel> {
        return combine(
            settingsRepository.flow(BooleanKey.IsRpmMode),
            settingsRepository.flow(IntegerKey.RpmValue),
            settingsRepository.flow(IntegerKey.CssValue),
            settingsRepository.flow(IntegerKey.MaxCssRpm),
            spindleOverride(),
            actualSpindleSpeed(),
            stopAngleFlow(),
        ) { values ->
            SpindleUiModel(
                isRpmMode = values[0] as Boolean,
                setRpm = values[1] as Int,
                setCss = values[2] as Int,
                maxRpm = values[3] as Int,
                spindleOverride = values[4] as Double,
                actualRpm = values[5] as Int,
                stopAngle = values[6] as Double?,
            )
        }
    }

    private fun spindleOverride(): Flow<Double> =
        statusRepository
            .cncStatusFlow
            .map { it.motion_status!!.spindle_status[0].spindle_scale }
            .distinctUntilChanged()

    private fun actualSpindleSpeed() =
        halRepository.actualSpindleSpeed().map { it.toInt() }.distinctUntilChanged()

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun stopAngleFlow(): Flow<Double?> {
        return settingsRepository.flow(BooleanKey.OrientedStop).flatMapLatest {
            when {
                it -> settingsRepository.flow(DoubleKey.OrientAngle)
                else -> flowOf(null)
            }
        }
    }
}
