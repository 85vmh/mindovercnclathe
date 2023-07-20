package initializer

import kotlinx.coroutines.flow.StateFlow

interface Initializer {
    val stepCount: Int
    val currentStepNumber: StateFlow<Int>
    val state: StateFlow<State>

    suspend fun initialise()

    enum class State {
        NOT_STARTED,
        STARTED,
        FINISHED;
    }
}