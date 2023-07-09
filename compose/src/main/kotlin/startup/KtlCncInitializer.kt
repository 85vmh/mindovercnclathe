package startup

import app.Files
import com.mindovercnc.linuxcnc.CncInitializer
import mu.KotlinLogging
import okio.FileSystem

object KtlCncInitializer {
    fun initialise() {
        val appDir = Files.appDir
        FileSystem.SYSTEM.createDirectories(appDir)

        println(appDir)
        logger.info { "Created appDir $appDir" }

        CncInitializer(appDir.toFile())
    }

    private val logger = KotlinLogging.logger("ktlcnc")
}