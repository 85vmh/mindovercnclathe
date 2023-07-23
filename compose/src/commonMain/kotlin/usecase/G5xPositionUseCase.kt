package usecase

import com.mindovercnc.repository.CncStatusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import linuxcnc.getDisplayablePosition
import ro.dragossusi.proto.linuxcnc.status.Position

class G5xPositionUseCase(private val cncStatusRepository: CncStatusRepository) {

    val g5xPositionFlow: Flow<Position>
        get() =
            cncStatusRepository.cncStatusFlow.map { it.getDisplayablePosition() }.distinctUntilChanged()
}
