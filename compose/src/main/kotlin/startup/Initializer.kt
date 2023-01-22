package startup

import app.Files
import com.mindovercnc.database.DbInitializer
import com.mindovercnc.linuxcnc.CncInitializer
import mu.KotlinLogging
import okio.FileSystem

object Initializer {

  private val logger = KotlinLogging.logger("Initializer")

  operator fun invoke(startupArgs: StartupArgs) {
    //        if (startupArgs.vtkEnabled.enabled) {
    //            VtkLoader.invoke()
    //        }

    val appDir = Files.appDir
    FileSystem.SYSTEM.createDirectories(appDir)

    println(appDir)
    logger.info { "Created appDir $appDir" }

    CncInitializer(appDir.toFile())

    DbInitializer()
  }
}
