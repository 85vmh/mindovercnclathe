package usecase

import com.mindovercnc.repository.HalRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

class ManualToolChangeUseCase(
    scope: CoroutineScope,
    private val halRepository: HalRepository,
) {
    private val _toolNo = MutableStateFlow<Int?>(null)

    init {
        halRepository.getToolChangeRequest()
            .distinctUntilChanged()
            .filter { it }
            .onEach {
                requestManualToolChange(halRepository.getToolChangeToolNumber().first())
            }
            .launchIn(scope)
    }

    val toolToChange = _toolNo.asSharedFlow().distinctUntilChanged()

    private fun requestManualToolChange(toolNo: Int) {
        println("**requestManualToolChange: $toolNo")
        _toolNo.value = toolNo
    }

    suspend fun confirmToolChange() {
        println("**confirmToolChange")
        halRepository.setToolChangedResponse()
        _toolNo.value = null
    }

    fun cancelToolChange() {
        println("**cancelToolChange")
        _toolNo.value = null
    }
}