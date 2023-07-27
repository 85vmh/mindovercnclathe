package usecase

import com.mindovercnc.data.linuxcnc.CncStatusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import linuxcnc.g53Position
import linuxcnc.getDisplayablePosition
import org.jetbrains.skia.Point

class PositionUseCase(
    private val statusRepository: CncStatusRepository,
    private val dtgPositionUseCase: DtgPositionUseCase
) {
    suspend fun getCurrentPoint() =
        statusRepository.cncStatusFlow
            .map { it.getDisplayablePosition() }
            .map { position ->
                Point(
                    // *2 due to diameter mode
                    position.x.toFloat() * 2,
                    position.z.toFloat()
                )
            }
            .first()

    fun getToolPosition(): Flow<Point> {
        return statusRepository.cncStatusFlow
            .map { it.g53Position!! }
            .map { position ->
                Point(position.x.toFloat(), position.z.toFloat())
            }
            .distinctUntilChanged()
    }

    suspend fun getZMachinePosition() =
        statusRepository.cncStatusFlow.map { it.g53Position!! }.map { it.z }.first()
}
