package usecase

import com.mindovercnc.linuxcnc.model.Position
import com.mindovercnc.model.Point2D
import com.mindovercnc.model.dtg
import com.mindovercnc.model.g53Position
import com.mindovercnc.model.getDisplayablePosition
import com.mindovercnc.repository.CncStatusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class PositionUseCase(
    private val statusRepository: CncStatusRepository
) {
    suspend fun getCurrentPoint() = statusRepository.cncStatusFlow()
        .map { it.getDisplayablePosition() }
        .map { Point2D(it.x * 2, it.z) } // *2 due to diameter mode
        .first()

    fun getToolPosition(): Flow<Point2D> {
        return statusRepository.cncStatusFlow()
            .map { it.g53Position }
            .map { Point2D(it.x, it.z) }
            .distinctUntilChanged()
    }

    suspend fun getZMachinePosition() = statusRepository.cncStatusFlow()
        .map { it.g53Position }
        .map { it.z }
        .first()

    private fun getDtgPosition(): Flow<Position> {
        return statusRepository.cncStatusFlow()
            .map { it.dtg }
            .distinctUntilChanged()
    }
}