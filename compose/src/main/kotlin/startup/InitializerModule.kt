package startup

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val InitializerModule = DI.Module("initializer") {
    bindSingleton { AppInitializer(instance(), instance(), instance()) }
}