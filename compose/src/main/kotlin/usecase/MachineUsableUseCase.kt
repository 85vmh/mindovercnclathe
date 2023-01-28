package usecase

import com.mindovercnc.repository.CncStatusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ro.dragossusi.proto.linuxcnc.isEstop
import ro.dragossusi.proto.linuxcnc.isHomed
import ro.dragossusi.proto.linuxcnc.isOn

/** Use case to know when the machine is usable */
class MachineUsableUseCase(private val cncStatusRepository: CncStatusRepository) {

  val machineUsableFlow: Flow<Boolean>
    get() =
      cncStatusRepository.cncStatusFlow
        .map { it.taskStatus.isEstop.not() && it.taskStatus.isOn && it.motionStatus.isHomed(2) }
        .distinctUntilChanged()
}
