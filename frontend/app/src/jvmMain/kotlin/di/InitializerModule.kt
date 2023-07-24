package di

import app.Files
import com.mindovercnc.linuxcnc.KtlCncInitializer
import initializer.Initializer
import initializer.SimpleInitializer
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import startup.AppDirInitializer
import startup.StatusWatchInitializer

val InitializerModule = DI.Module("initializer") {
    bindSingleton("app") {
        val appDirInitializer: AppDirInitializer = instance()
        val ktlCncInitializer: KtlCncInitializer = instance()
        val databaseInitializer: Initializer = instance("database")
        val statusWatchInitializer: StatusWatchInitializer = instance()

        SimpleInitializer(appDirInitializer, ktlCncInitializer, databaseInitializer, statusWatchInitializer)
    }

    bindSingleton { AppDirInitializer(instance()) }

    bindSingleton { KtlCncInitializer(Files.appDir.toFile()) }
    bindSingleton { StatusWatchInitializer(instance(), instance()) }
}