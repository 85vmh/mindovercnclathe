package startup

import app.Files
import com.mindovercnc.database.DbInitializer
import com.mindovercnc.linuxcnc.CncInitializer
import okio.FileSystem

object Initializer {

  operator fun invoke(startupArgs: StartupArgs) {
    //        if (startupArgs.vtkEnabled.enabled) {
    //            VtkLoader.invoke()
    //        }

    val appDir = Files.appDir
    FileSystem.SYSTEM.createDirectories(appDir)

    CncInitializer(appDir.toFile())

    DbInitializer()
  }
}
