package usecase

import com.mindovercnc.repository.SettingsRepository
import com.mindovercnc.model.DoubleKey
import kotlinx.coroutines.flow.Flow

class TaperUseCase(
    private val settingsRepository: SettingsRepository
) {
    fun taperAngleFlow(): Flow<Double> {
        return settingsRepository.flow(DoubleKey.TaperAngle)
    }
}