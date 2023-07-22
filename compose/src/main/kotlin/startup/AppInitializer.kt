package startup

import StatusWatcher
import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.dispatchers.createScope
import com.mindovercnc.linuxcnc.KtlCncInitializer
import initializer.Initializer
import mu.KotlinLogging

class AppInitializer(
    private val databaseInitializer: Initializer,
    private val appDirInitializer: AppDirInitializer,
    private val ktlCncInitializer: KtlCncInitializer,
    private val statusWatcher: StatusWatcher,
    private val ioDispatcher: IoDispatcher
) {
    suspend fun initialise() {
        /* No longer used
        if (startupArgs.vtkEnabled.enabled) {
            VtkLoader.invoke()
        }
        */
        appDirInitializer.initialise()

        logger.info("Initialising ktlcnc")
        ktlCncInitializer.initialise()

        logger.info("Initialising database")
        databaseInitializer.initialise()

        // start listening for status
        statusWatcher.launchIn(ioDispatcher.createScope())
    }

    companion object {
        private val logger = KotlinLogging.logger("Initializer")
    }
}
