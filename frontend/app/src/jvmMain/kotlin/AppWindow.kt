import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import startup.args.StartupArgs
import themes.AppTheme

@Composable
fun AppWindow(startupArgs: StartupArgs, onCloseRequest: () -> Unit) {
    val windowState =
        rememberWindowState(
            width = startupArgs.screenSize.width,
            height = startupArgs.screenSize.height
        )

    Window(
        onCloseRequest = onCloseRequest,
        title = "MindOverCNC Lathe",
        focusable = false,
        undecorated = !startupArgs.topBarEnabled.enabled,
        state = windowState
    ) {
        val newDensity = Density(density = startupArgs.density.toFloat())
        CompositionLocalProvider(LocalDensity provides newDensity) {
            AppTheme(startupArgs.darkMode) { MindOverCNCLathe() }
        }
    }
}
