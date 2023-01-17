package usecase

import com.mindovercnc.repository.CncStatusRepository
import com.mindovercnc.repository.SettingsRepository
import com.mindovercnc.model.BooleanKey
import com.mindovercnc.model.DoubleKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ro.dragossusi.proto.linuxcnc.setFeedRate
import ui.screen.manual.root.FeedUiModel

class FeedUseCase(
    statusRepository: CncStatusRepository,
    private val settingsRepository: SettingsRepository
) {

    private val feedOverride = statusRepository.cncStatusFlow()
        .map { it.motionStatus.trajectoryStatus.scale }
        .distinctUntilChanged()
    private val setFeedRate = statusRepository.cncStatusFlow()
        .map { it.taskStatus.setFeedRate }
        .distinctUntilChanged()

    //TODO: check if this is correct when feeding in mm/min
    private val actualFeedRate = combine(setFeedRate, feedOverride) { feed, scale -> (feed ?: 0.0) * scale / 100 }.distinctUntilChanged()

    fun feedFlow(): Flow<FeedUiModel> {
        return combine(
            settingsRepository.flow(BooleanKey.IsUnitsPerRevMode),
            settingsRepository.flow(DoubleKey.FeedPerRev),
            settingsRepository.flow(DoubleKey.FeedPerMin),
            feedOverride,
            actualFeedRate
        ) { isUnitsPerRev, feedPerRev, feedPerMin, feedOverride, actualFeed ->
            FeedUiModel(
                isUnitsPerRevMode = isUnitsPerRev,
                feedOverride = feedOverride,
                setFeed = when (isUnitsPerRev) {
                    true -> feedPerRev
                    else -> feedPerMin
                },
                actualFeed = actualFeed
            )
        }
    }
}