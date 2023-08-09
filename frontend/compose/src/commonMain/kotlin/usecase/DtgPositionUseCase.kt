package usecase

import com.mindovercnc.repository.MotionStatusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ro.dragossusi.proto.linuxcnc.status.Position

class DtgPositionUseCase(private val motionStatusRepository: MotionStatusRepository) {
    val dtgPositionFlow: Flow<Position>
        get() = motionStatusRepository.motionStatusFlow
            .map { it.trajectory_status!!.dtg!! }
            .distinctUntilChanged()
}
