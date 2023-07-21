package di

import StatusWatcher
import TabViewModel
import androidx.compose.runtime.Composable
import com.mindovercnc.database.module.DatabaseModule
import com.mindovercnc.dispatchers.DispatchersModule
import com.mindovercnc.linuxcnc.module.KtLcncModule
import okio.FileSystem
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.compose.withDI
import org.kodein.di.instance
import startup.InitializerModule
import startup.StartupArgs

val BaseAppModule = DI.Module("AppModule") {
    importAll(
        DispatchersModule,
        EditorModule,
        DatabaseModule,
        InitializerModule,
        ScreenModelModule,
        DomainModule,
    )
    bindSingleton { StatusWatcher(instance(), instance(), instance(), instance()) }

    bindProvider { TabViewModel(instance(), instance()) }

    bindSingleton { FileSystem.SYSTEM }
}

@Composable
fun withAppDi(startupArgs: StartupArgs, content: @Composable () -> Unit) = withDI(
    KtLcncModule,
    startupModule(startupArgs),
    BaseAppModule,
    repositoryModule(startupArgs.legacyCommunication),
    ParseFactoryModule,
    GrpcModule,
    content = content
)
