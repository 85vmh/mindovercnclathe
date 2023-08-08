package initializer

import kotlinx.coroutines.flow.StateFlow

interface Initializer : InitializerStep {
    val stepCount: Int
    val currentStepNumber: StateFlow<Int>
    val state: StateFlow<State>

    enum class State {
        NOT_STARTED,
        STARTED,
        FINISHED;
    }
}