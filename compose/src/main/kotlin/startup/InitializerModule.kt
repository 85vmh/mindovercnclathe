package startup

import app.Files
import com.mindovercnc.linuxcnc.KtlCncInitializer
import initializer.Initializer
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val InitializerModule = DI.Module("initializer") {
    bindSingleton { AppInitializer(instance("database"), instance(), instance(), instance(), instance()) }
    bindSingleton { AppDirInitializer(instance()) }
    bindSingleton { KtlCncInitializer(Files.appDir.toFile()) }
}