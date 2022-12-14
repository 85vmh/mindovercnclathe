package app

import MindOverCNCLathe
import StatusWatcher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import di.*
import kotlinx.coroutines.Dispatchers
import org.kodein.di.compose.rememberInstance
import org.kodein.di.compose.withDI
import startup.StartupArgs
import themes.AppTheme

@Composable
fun AppWindow(
    windowState: WindowState,
    startupArgs: StartupArgs,
    onCloseRequest: () -> Unit
) = Window(
    onCloseRequest = onCloseRequest,
    title = "MindOverCNC Lathe",
    focusable = false,
    undecorated = !startupArgs.topBarEnabled.enabled,
    state = windowState
) {
    val scope = rememberCoroutineScope {
        Dispatchers.IO
    }
    withDI(
        startupModule(startupArgs),
        appScopeModule(scope),
        AppModule,
        ScreenModelModule,
        UseCaseModule,
        RepositoryModule,
        ParseFactoryModule,
        BuffDescriptorModule
    ) {
        val statusWatcher by rememberInstance<StatusWatcher>()
        statusWatcher.launchIn(scope)
        AppTheme(startupArgs.darkMode) {
            MindOverCNCLathe()
        }
    }
}