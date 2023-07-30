package di

import StatusWatcher
import TabViewModel
import androidx.compose.runtime.Composable
import com.mindovercnc.data.linuxcnc.legacy.LinuxcncLegacyDataModule
import com.mindovercnc.data.linuxcnc.remote.LinuxcncRemoteDataModule
import com.mindovercnc.database.module.DatabaseModule
import com.mindovercnc.dispatchers.DispatchersModule
import com.mindovercnc.linuxcnc.CommonDataModule
import com.mindovercnc.linuxcnc.di.ParseFactoryModule
import com.mindovercnc.linuxcnc.module.KtLcncModule
import kotlinx.datetime.Clock
import okio.FileSystem
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.compose.withDI
import org.kodein.di.instance
import startup.args.StartupArgs

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
    bindSingleton<Clock> { Clock.System }
}

fun repositoryModule(legacyCommunication: Boolean) =
    DI.Module("repository") {
        import(CommonDataModule)
        if (legacyCommunication) {
            import(LinuxcncLegacyDataModule)
        } else {
            importAll(LinuxcncRemoteDataModule, GrpcModule)
        }
    }

@Composable
fun withAppDi(startupArgs: StartupArgs, content: @Composable () -> Unit) = withDI(
    KtLcncModule,
    startupModule(startupArgs.iniFilePath),
    BaseAppModule,
    repositoryModule(startupArgs.legacyCommunication),
    ParseFactoryModule,
    content = content
)
