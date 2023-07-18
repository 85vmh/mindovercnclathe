package initializer

/** Step to be executed during initialisation. */
interface InitializerStep {
    /** Initialises this step. */
    suspend fun initialise()
}