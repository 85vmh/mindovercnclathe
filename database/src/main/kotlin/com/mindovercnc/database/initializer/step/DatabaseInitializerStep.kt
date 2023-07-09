package com.mindovercnc.database.initializer.step

/** Initialize steps for DB. */
internal fun interface DatabaseInitializerStep {
    /** Initialises this step. */
    suspend fun initialise()
}