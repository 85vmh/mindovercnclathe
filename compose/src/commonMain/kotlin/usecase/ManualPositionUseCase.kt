package usecase

import com.mindovercnc.repository.CncStatusRepository
import kotlinx.coroutines.flow.*
import linuxcnc.getDisplayablePosition
import ro.dragossusi.proto.linuxcnc.status.Position
import screen.uimodel.AxisPosition
import screen.uimodel.PositionModel

class ManualPositionUseCase(
    private val cncStatusRepository: CncStatusRepository
) {
    private data class PositionInfo(val position: Position, val xRelative: Boolean, val zRelative: Boolean)

    private var xZeroPos = 0.0
    private var zZeroPos = 0.0
    private val xRelativeState = MutableStateFlow(false)
    private val zRelativeState = MutableStateFlow(false)

    fun getPositionModel() = combine(
        getDisplayablePosition(),
        xRelativeState,
        zRelativeState
    ) { displayablePos, xRelative, zRelative -> PositionInfo(displayablePos, xRelative, zRelative) }
        .distinctUntilChanged()
        .map {
            val xAxisPosition = when (it.xRelative) {
                true -> AxisPosition(
                    AxisPosition.Axis.X,
                    it.position.x - xZeroPos,
                    it.position.x,
                    AxisPosition.Units.MM
                )

                false -> AxisPosition(AxisPosition.Axis.X, it.position.x, null, AxisPosition.Units.MM)
            }
            val zAxisPosition = when (it.zRelative) {
                true -> AxisPosition(
                    AxisPosition.Axis.Z,
                    it.position.z - zZeroPos,
                    it.position.z,
                    AxisPosition.Units.MM
                )

                false -> AxisPosition(AxisPosition.Axis.Z, it.position.z, null, AxisPosition.Units.MM)
            }
            PositionModel(xAxisPosition, zAxisPosition, true)
        }

    suspend fun setZeroPosX() {
        xZeroPos = getDisplayablePosition().map { it.x }.first()
        xRelativeState.value = true
    }

    fun toggleXAbsRel() {
        xRelativeState.value = xRelativeState.value.not()
    }

    suspend fun setZeroPosZ() {
        zZeroPos = getDisplayablePosition().map { it.z }.first()
        zRelativeState.value = true
    }

    fun toggleZAbsRel() {
        zRelativeState.value = zRelativeState.value.not()
    }

    private fun getDisplayablePosition(): Flow<Position> {
        return cncStatusRepository.cncStatusFlow
            .map { it.getDisplayablePosition() }
            .distinctUntilChanged()
    }
}