package com.mindovercnc.linuxcnc.domain

import com.mindovercnc.linuxcnc.settings.model.DoubleKey
import com.mindovercnc.linuxcnc.settings.SettingsRepository
import kotlinx.coroutines.flow.Flow

class TaperUseCase(
    private val settingsRepository: SettingsRepository
) {
    fun taperAngleFlow(): Flow<Double> {
        return settingsRepository.flow(DoubleKey.TaperAngle)
    }
}