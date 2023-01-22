package usecase

import com.mindovercnc.repository.CncCommandRepository
import com.mindovercnc.repository.CncStatusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import okio.Path
import ro.dragossusi.proto.linuxcnc.dtg
import ro.dragossusi.proto.linuxcnc.getDisplayablePosition
import ro.dragossusi.proto.linuxcnc.status.Position
import ro.dragossusi.proto.linuxcnc.status.TaskMode
import screen.uimodel.AxisPosition
import screen.uimodel.PositionModel

class ProgramsUseCase(
  private val statusRepository: CncStatusRepository,
  private val commandRepository: CncCommandRepository,
) {

  private fun getG5xPosition(): Flow<Position> {
    return statusRepository
      .cncStatusFlow
      .map { it.getDisplayablePosition() }
      .distinctUntilChanged()
  }

  private fun getDtgPosition(): Flow<Position> {
    return statusRepository.cncStatusFlow.map { it.dtg }.distinctUntilChanged()
  }

  val uiModel =
    combine(
      getG5xPosition(),
      getDtgPosition(),
    ) { g5xPos, dtgPos ->
      val xAxisPos =
        AxisPosition(AxisPosition.Axis.X, g5xPos.x, dtgPos.x, units = AxisPosition.Units.MM)
      val zAxisPos =
        AxisPosition(AxisPosition.Axis.Z, g5xPos.z, dtgPos.z, units = AxisPosition.Units.MM)
      PositionModel(xAxisPos, zAxisPos, true)
    }

  fun loadProgram(file: Path) {
    println("Loading program file: $file")
    //        commandRepository.setTaskMode(TaskMode.TaskModeAuto)
    commandRepository.loadProgramFile(file.toString())
  }

  fun runProgram() {
    commandRepository.setTaskMode(TaskMode.TaskModeAuto)
    commandRepository.runProgram()
  }

  fun stopProgram() {
    commandRepository.setTaskMode(TaskMode.TaskModeManual)
    commandRepository.stopProgram()
  }
}
