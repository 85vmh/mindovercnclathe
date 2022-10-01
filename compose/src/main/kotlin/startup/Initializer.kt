package startup

import app.Files
import com.mindovercnc.database.DbInitializer
import com.mindovercnc.linuxcnc.CncInitializer

object Initializer {

    operator fun invoke(startupArgs: StartupArgs) {
//        if (startupArgs.vtkEnabled.enabled) {
//            VtkLoader.invoke()
//        }

        CncInitializer(Files.appDir)

        DbInitializer()
    }
}