package com.mindovercnc.database.initializer

import com.mindovercnc.database.initializer.step.DatabaseInitializerStep
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import mu.KotlinLogging

/** Initializer for the KtCncDb. */
internal class KtCncInitializer(
    vararg val steps: DatabaseInitializerStep
) : DatabaseInitializer {

    override val stepCount: Int = steps.size

    private val _currentStep = MutableStateFlow(0)
    override val currentStep: StateFlow<Int> = _currentStep.asStateFlow()

    private val _isFinished = MutableStateFlow(false)
    override val isFinished: StateFlow<Boolean> = _isFinished.asStateFlow()

    override suspend fun initialise() {
        steps.forEachIndexed { index, step ->
            logger.info { "Initialising database $index / ${steps.lastIndex}" }
            _currentStep.value = index
            step.initialise()
        }
    }

    companion object {
        private val logger = KotlinLogging.logger("DatabaseInitializer")
    }
}
