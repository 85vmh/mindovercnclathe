package initializer

import initializer.Initializer.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import mu.KotlinLogging

/** Initializer for the KtCncDb. */
class SimpleInitializer(
    vararg val steps: InitializerStep
) : Initializer {

    override val stepCount: Int = steps.size

    private val _currentStepNumber = MutableStateFlow(0)
    override val currentStepNumber: StateFlow<Int> = _currentStepNumber.asStateFlow()

    private val _state = MutableStateFlow(State.NOT_STARTED)
    override val state: StateFlow<State> = _state.asStateFlow()

    override suspend fun initialise() {
        steps.forEachIndexed { index, step ->
            logger.info { "Initialising database $index / ${steps.lastIndex}" }
            _currentStepNumber.value = index
            step.initialise()
        }
    }

    companion object {
        private val logger = KotlinLogging.logger("DatabaseInitializer")
    }
}
