import com.mindovercnc.repository.CncStatusRepository
import com.mindovercnc.repository.MessagesRepository
import com.mindovercnc.repository.handleMessage
import com.mindovercnc.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.plus

class StatusWatcher(
    private val cncStatusRepository: CncStatusRepository,
    private val messagesRepository: MessagesRepository,
) {

    private var job = Job()
    fun launchIn(scope: CoroutineScope) {
        job.cancel()
        job = Job()
        val innerScope = scope + job

//        cncStatusRepository.cncStatusFlow()
//            .map {
//                val messages = mutableListOf<UiMessage>()
//                if (it.isXHomed.not()) {
//                    messages += UiMessage.XAxisNotHomed
//                }
//            }.launchIn(innerScope)

        cncStatusRepository.cncStatusFlow()
            .map { it.isXHomed.not() }
            .distinctUntilChanged()
            .onEach { messagesRepository.handleMessage(it, UiMessage.XAxisNotHomed) }
            .launchIn(innerScope)

        cncStatusRepository.cncStatusFlow()
            .map { it.isZHomed.not() }
            .distinctUntilChanged()
            .onEach { messagesRepository.handleMessage(it, UiMessage.ZAxisNotHomed) }
            .launchIn(innerScope)

        cncStatusRepository.cncStatusFlow()
            .map { it.isXHoming }
            .distinctUntilChanged()
            .onEach { messagesRepository.handleMessage(it, UiMessage.XAxisHoming) }
            .launchIn(innerScope)

        cncStatusRepository.cncStatusFlow()
            .map { it.isZHoming }
            .distinctUntilChanged()
            .onEach { messagesRepository.handleMessage(it, UiMessage.ZAxisHoming) }
            .launchIn(innerScope)

        cncStatusRepository.cncStatusFlow()
            .map { it.isNotOn }
            .distinctUntilChanged()
            .onEach { messagesRepository.handleMessage(it, UiMessage.MachineNotON) }
            .launchIn(innerScope)
        cncStatusRepository.cncStatusFlow()
            .map { it.isEstop }
            .distinctUntilChanged()
            .onEach { messagesRepository.handleMessage(it, UiMessage.MachineInEstop) }
            .launchIn(innerScope)
        cncStatusRepository.cncStatusFlow()
            .map { it.isMinSoftLimitOnX }
            .distinctUntilChanged()
            .onEach { messagesRepository.handleMessage(it, UiMessage.ReachedMinSoftLimitX) }
            .launchIn(innerScope)
        cncStatusRepository.cncStatusFlow()
            .map { it.isMaxSoftLimitOnX }
            .distinctUntilChanged()
            .onEach { messagesRepository.handleMessage(it, UiMessage.ReachedMaxSoftLimitX) }
            .launchIn(innerScope)
        cncStatusRepository.cncStatusFlow()
            .map { it.isMinSoftLimitOnZ }
            .distinctUntilChanged()
            .onEach { messagesRepository.handleMessage(it, UiMessage.ReachedMinSoftLimitZ) }
            .launchIn(innerScope)
        cncStatusRepository.cncStatusFlow()
            .map { it.isMaxSoftLimitOnZ }
            .distinctUntilChanged()
            .onEach { messagesRepository.handleMessage(it, UiMessage.ReachedMaxSoftLimitZ) }
            .launchIn(innerScope)
    }
}