import com.mindovercnc.data.linuxcnc.CncStatusRepository
import com.mindovercnc.model.CncStateMessage
import com.mindovercnc.repository.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.plus
import linuxcnc.*
import mu.KotlinLogging

class StatusWatcher(
    // private val cncStatusRepository: CncStatusRepository,
    private val motionStatusRepository: MotionStatusRepository,
    private val taskStatusRepository: TaskStatusRepository,
    private val messagesRepository: CncMessagesRepository,
) {

    private val supervisorJob = SupervisorJob()

    fun launchIn(scope: CoroutineScope) {
        LOG.debug { "Launching status watcher" }
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
            .map { it.isXHomed }
            .distinctUntilChanged()
            .onEach { isXHomed ->
                messagesRepository.handleMessage(!isXHomed, CncStateMessage.XAxisNotHomed)
            }
            .launchIn(innerScope)

        motionStatusRepository.motionStatusFlow
            .map { it.isZHomed }
            .distinctUntilChanged()
            .onEach { isZHomed ->
                messagesRepository.handleMessage(!isZHomed, CncStateMessage.ZAxisNotHomed)
            }
            .launchIn(innerScope)

        motionStatusRepository.motionStatusFlow
            .map { it.isXHoming }
            .distinctUntilChanged()
            .onEach { isXHoming -> messagesRepository.handleMessage(isXHoming, CncStateMessage.XAxisHoming) }
            .launchIn(innerScope)

        motionStatusRepository.motionStatusFlow
            .map { it.isZHoming }
            .distinctUntilChanged()
            .onEach { isZHoming -> messagesRepository.handleMessage(isZHoming, CncStateMessage.ZAxisHoming) }
            .launchIn(innerScope)

        motionStatusRepository.motionStatusFlow
            .map { it.isMinSoftLimitOnX }
            .distinctUntilChanged()
            .onEach { messagesRepository.handleMessage(it, CncStateMessage.ReachedMinSoftLimitX) }
            .launchIn(innerScope)
        motionStatusRepository.motionStatusFlow
            .map { it.isMaxSoftLimitOnX }
            .distinctUntilChanged()
            .onEach { messagesRepository.handleMessage(it, CncStateMessage.ReachedMaxSoftLimitX) }
            .launchIn(innerScope)
        motionStatusRepository.motionStatusFlow
            .map { it.isMinSoftLimitOnZ }
            .distinctUntilChanged()
            .onEach { messagesRepository.handleMessage(it, CncStateMessage.ReachedMinSoftLimitZ) }
            .launchIn(innerScope)
        motionStatusRepository.motionStatusFlow
            .map { it.isMaxSoftLimitOnZ }
            .distinctUntilChanged()
            .onEach { messagesRepository.handleMessage(it, CncStateMessage.ReachedMaxSoftLimitZ) }
            .launchIn(innerScope)

        // task status
        taskStatusRepository.taskStatusFlow
            .map { it.isNotOn }
            .distinctUntilChanged()
            .onEach { messagesRepository.handleMessage(it, CncStateMessage.MachineNotON) }
            .launchIn(innerScope)
        taskStatusRepository.taskStatusFlow
            .map { it.isEstop }
            .distinctUntilChanged()
            .onEach { messagesRepository.handleMessage(it, CncStateMessage.MachineInEstop) }
            .launchIn(innerScope)
    }

    fun stopListening() {
        supervisorJob.cancelChildren()
    }

    companion object {
        private val LOG = KotlinLogging.logger("StatusWatcher")
    }
}
