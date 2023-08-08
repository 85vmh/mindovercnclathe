package startup

import Files
import initializer.InitializerStep
import mu.KotlinLogging
import okio.FileSystem

class AppDirInitializer(
    private val fileSystem: FileSystem
) : InitializerStep {
    override suspend fun initialise() {
        val appDir = Files.appDir
        fileSystem.createDirectories(appDir)
        logger.info { "Created appDir $appDir" }
    }

    companion object {
        private val logger = KotlinLogging.logger("AppDirInitializer")
    }
}