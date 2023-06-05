package app

import MindOverCNCLathe
import StatusWatcher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.mindovercnc.dispatchers.DispatchersModule
import di.*
import kotlinx.coroutines.Dispatchers
import org.kodein.di.compose.rememberInstance
import org.kodein.di.compose.withDI
import startup.StartupArgs
import themes.AppTheme

@Composable
fun AppWindow(windowState: WindowState, startupArgs: StartupArgs, onCloseRequest: () -> Unit) =
    Window(
        onCloseRequest = onCloseRequest,
        title = "MindOverCNC Lathe",
        focusable = false,
        undecorated = !startupArgs.topBarEnabled.enabled,
        state = windowState
    ) {
        val scope = rememberCoroutineScope { Dispatchers.IO }
        withDI(
            startupModule(startupArgs),
            DispatchersModule,
            AppModule,
            ScreenModelModule,
            UseCaseModule,
            repositoryModule(startupArgs.legacyCommunication),
            ParseFactoryModule,
            BuffDescriptorModule,
            EditorModule,
            GrpcModule
        ) {
            val statusWatcher by rememberInstance<StatusWatcher>()
            statusWatcher.launchIn(scope)

            val newDensity = Density(density = startupArgs.density.toFloat())
            CompositionLocalProvider(LocalDensity provides newDensity){
                AppTheme(startupArgs.darkMode) { MindOverCNCLathe() }
            }
        }
    }
