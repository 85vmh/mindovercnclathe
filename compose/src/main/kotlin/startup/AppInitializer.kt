package startup

import StatusWatcher
import app.Files
import com.mindovercnc.database.initializer.DatabaseInitializer
import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.dispatchers.createScope
import com.mindovercnc.linuxcnc.CncInitializer
import mu.KotlinLogging
import okio.FileSystem

class AppInitializer(
    private val databaseInitializer: DatabaseInitializer,
    private val statusWatcher: StatusWatcher,
    private val ioDispatcher: IoDispatcher
) {
    suspend fun initialise() {
        /* No longer used
        if (startupArgs.vtkEnabled.enabled) {
            VtkLoader.invoke()
        }
        */

        databaseInitializer.initialise()

        // start listening for status
        statusWatcher.launchIn(ioDispatcher.createScope())
    }

    companion object {
        private val logger = KotlinLogging.logger("Initializer")
    }
}
