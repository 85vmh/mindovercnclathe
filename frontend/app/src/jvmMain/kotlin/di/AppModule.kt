package di

import StatusWatcher
import TabViewModel
import androidx.compose.runtime.Composable
import com.mindovercnc.data.linuxcnc.legacy.LinuxcncLegacyDataModule
import com.mindovercnc.data.linuxcnc.remote.LinuxcncRemoteDataModule
import com.mindovercnc.database.module.DatabaseModule
import com.mindovercnc.dispatchers.DispatchersModule
import com.mindovercnc.editor.reader.EditorReader
import com.mindovercnc.editor.reader.FileEditorReader
import com.mindovercnc.linuxcnc.CommonDataModule
import com.mindovercnc.linuxcnc.di.*
import com.mindovercnc.linuxcnc.gcode.local.di.GCodeLocalModule
import com.mindovercnc.linuxcnc.module.KtLcncModule
import com.mindovercnc.linuxcnc.settings.local.di.SettingsLocalModule
import com.mindovercnc.linuxcnc.tools.local.di.ToolsLocalModule
import com.mindovercnc.linuxcnc.tools.remote.di.ToolsRemoteModule
import kotlinx.datetime.Clock
import okio.FileSystem
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.compose.withDI
import org.kodein.di.instance
import startup.args.StartupArgs

val BaseAppModule =
    DI.Module("base_app") {
        importAll(
            DispatchersModule,
            EditorModule,
            DatabaseModule,
            InitializerModule,
            ScreenModelModule,
            DomainModule,
        )
        bindSingleton { StatusWatcher(instance(), instance(), instance()) }

        bindProvider { TabViewModel(instance(), instance()) }

        // TODO change based on platform
        bindSingleton<EditorReader> { FileEditorReader }
    }

val SystemModule =
    DI.Module("system") {
        bindSingleton { FileSystem.SYSTEM }
        bindSingleton<Clock> { Clock.System }
    }

fun repositoryModule(legacyCommunication: Boolean) =
    DI.Module("repository") {
        import(CommonDataModule)
        if (legacyCommunication) {
            importAll(
                LinuxcncLegacyDataModule,
                ToolsLocalModule,
                GCodeLocalModule,
                SettingsLocalModule
            )
        } else {
            importAll(LinuxcncRemoteDataModule, ToolsRemoteModule, GrpcModule)
        }
    }

@Composable
fun withAppDi(startupArgs: StartupArgs, content: @Composable () -> Unit) =
    withDI(
        KtLcncModule,
        startupModule(startupArgs.iniFilePath),
        BaseAppModule,
        SystemModule,
        repositoryModule(startupArgs.legacyCommunication),
        ParseFactoryModule,
        content = content
    )
