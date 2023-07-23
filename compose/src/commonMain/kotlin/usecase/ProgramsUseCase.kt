package usecase

import com.mindovercnc.repository.CncCommandRepository
import kotlinx.coroutines.flow.combine
import okio.Path
import ro.dragossusi.proto.linuxcnc.status.TaskMode
import screen.uimodel.AxisPosition
import screen.uimodel.PositionModel

class ProgramsUseCase(
  private val commandRepository: CncCommandRepository,
  dtgPositionUseCase: DtgPositionUseCase,
  g5xPositionUseCase: G5xPositionUseCase
) {

  val uiModel =
    combine(
      g5xPositionUseCase.g5xPositionFlow,
      dtgPositionUseCase.dtgPositionFlow,
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
