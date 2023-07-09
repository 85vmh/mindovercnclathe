package com.mindovercnc.database.initializer

import kotlinx.coroutines.flow.StateFlow

interface DatabaseInitializer {
    val stepCount: Int
    val currentStep: StateFlow<Int>
    val isFinished: StateFlow<Boolean>

    suspend fun initialise()
}