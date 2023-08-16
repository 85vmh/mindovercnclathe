package com.mindovercnc.linuxcnc.domain

import com.mindovercnc.data.linuxcnc.HalRepository
import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.dispatchers.createScope
import kotlinx.coroutines.flow.*

class ManualToolChangeUseCase(
    ioDispatcher: IoDispatcher,
    private val halRepository: HalRepository,
) {
    private val scope = ioDispatcher.createScope()
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