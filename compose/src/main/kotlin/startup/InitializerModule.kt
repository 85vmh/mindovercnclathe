package startup

import com.mindovercnc.database.module.DatabaseModule
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val InitializerModule = DI.Module("initializer") {
    import(DatabaseModule)
    bindSingleton { AppInitializer(instance()) }
}