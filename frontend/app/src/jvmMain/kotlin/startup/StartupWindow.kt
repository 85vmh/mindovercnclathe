package startup

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import startup.args.StartupArgs
import themes.AppTheme

@Composable
fun StartupWindow(startupArgs: StartupArgs, onInitialise: () -> Unit) {
    val windowState = rememberWindowState(size = DpSize(320.dp, 240.dp))
    Window(onCloseRequest = {}, state = windowState, undecorated = true) {
        AppTheme(startupArgs.darkMode) { InitializerScreen(onInitialise, Modifier.fillMaxSize()) }
    }
}
