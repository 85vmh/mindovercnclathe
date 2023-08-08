import com.mindovercnc.data.linuxcnc.CncStatusRepository
import com.mindovercnc.model.UiMessage
import com.mindovercnc.repository.MessagesRepository
import com.mindovercnc.repository.MotionStatusRepository
import com.mindovercnc.repository.TaskStatusRepository
import com.mindovercnc.repository.handleMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.plus
import linuxcnc.*

class StatusWatcher(
    private val cncStatusRepository: CncStatusRepository,
    private val motionStatusRepository: MotionStatusRepository,
    private val taskStatusRepository: TaskStatusRepository,
    private val messagesRepository: MessagesRepository,
) {

    private val supervisorJob = SupervisorJob()

    fun launchIn(scope: CoroutineScope) {
        supervisorJob.cancelChildren()
        val innerScope = scope + supervisorJob

        //    cncStatusRepository
        //      .cncStatusFlow
        //      .map {
        //        val messages = mutableListOf<UiMessage>()
        //        if (it.isXHomed.not()) {
        //          messages += UiMessage.XAxisNotHomed
        //        }
        //      }
        //      .launchIn(innerScope)

        // motion status
        motionStatusRepository.motionStatusFlow
            .map { it.isXHomed.not() }
            .distinctUntilChanged()
            .onEach { messagesRepository.handleMessage(it, UiMessage.XAxisNotHomed) }
            .launchIn(innerScope)

        motionStatusRepository.motionStatusFlow
            .map { it.isZHomed.not() }
            .distinctUntilChanged()
            .onEach { messagesRepository.handleMessage(it, UiMessage.ZAxisNotHomed) }
            .launchIn(innerScope)

        motionStatusRepository.motionStatusFlow
            .map { it.isXHoming }
            .distinctUntilChanged()
            .onEach { messagesRepository.handleMessage(it, UiMessage.XAxisHoming) }
            .launchIn(innerScope)

        motionStatusRepository.motionStatusFlow
            .map { it.isZHoming }
            .distinctUntilChanged()
            .onEach { messagesRepository.handleMessage(it, UiMessage.ZAxisHoming) }
            .launchIn(innerScope)

        motionStatusRepository.motionStatusFlow
            .map { it.isMinSoftLimitOnX }
            .distinctUntilChanged()
            .onEach { messagesRepository.handleMessage(it, UiMessage.ReachedMinSoftLimitX) }
            .launchIn(innerScope)
        motionStatusRepository.motionStatusFlow
            .map { it.isMaxSoftLimitOnX }
            .distinctUntilChanged()
            .onEach { messagesRepository.handleMessage(it, UiMessage.ReachedMaxSoftLimitX) }
            .launchIn(innerScope)
        motionStatusRepository.motionStatusFlow
            .map { it.isMinSoftLimitOnZ }
            .distinctUntilChanged()
            .onEach { messagesRepository.handleMessage(it, UiMessage.ReachedMinSoftLimitZ) }
            .launchIn(innerScope)
        motionStatusRepository.motionStatusFlow
            .map { it.isMaxSoftLimitOnZ }
            .distinctUntilChanged()
            .onEach { messagesRepository.handleMessage(it, UiMessage.ReachedMaxSoftLimitZ) }
            .launchIn(innerScope)

        // task status
        taskStatusRepository.taskStatusFlow
            .map { it.isNotOn }
            .distinctUntilChanged()
            .onEach { messagesRepository.handleMessage(it, UiMessage.MachineNotON) }
            .launchIn(innerScope)
        taskStatusRepository.taskStatusFlow
            .map { it.isEstop }
            .distinctUntilChanged()
            .onEach { messagesRepository.handleMessage(it, UiMessage.MachineInEstop) }
            .launchIn(innerScope)
    }

    fun stopListening() {
        supervisorJob.cancelChildren()
    }
}
