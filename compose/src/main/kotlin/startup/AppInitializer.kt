package startup

import app.Files
import com.mindovercnc.database.initializer.DatabaseInitializer
import com.mindovercnc.linuxcnc.CncInitializer
import mu.KotlinLogging
import okio.FileSystem

class AppInitializer(
    private val databaseInitializer: DatabaseInitializer
) {
    suspend fun initialise() {
        /* No longer used
        if (startupArgs.vtkEnabled.enabled) {
            VtkLoader.invoke()
        }
        */

        val appDir = Files.appDir
        FileSystem.SYSTEM.createDirectories(appDir)

        println(appDir)
        logger.info { "Created appDir $appDir" }

        CncInitializer(appDir.toFile())

        databaseInitializer.initialise()
    }

    companion object {
        private val logger = KotlinLogging.logger("Initializer")
    }
}
