import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import app.AppModePicker
import app.DefaultRootComponent
import app.RemoteHostPicker
import app.RootComponent
import org.kodein.di.compose.localDI
import org.kodein.di.direct
import org.kodein.di.instance
import startup.args.StartupArgs
import themes.AppTheme

@Composable
fun AppWindow(startupArgs: StartupArgs, onCloseRequest: () -> Unit) {
    val windowState =
        rememberWindowState(
            width = startupArgs.screenSize.width,
            height = startupArgs.screenSize.height
        )
    val root = null
    // createRootComponent()

    Window(
        onCloseRequest = onCloseRequest,
        title = "MindOverCNC Lathe",
        focusable = false,
        undecorated = !startupArgs.topBarEnabled.enabled,
        state = windowState
    ) {
        val newDensity = Density(density = startupArgs.density.toFloat())
        CompositionLocalProvider(LocalDensity provides newDensity) {
            AppTheme(startupArgs.darkMode) {
                if (startupArgs.legacyCommunication) {
                    MindOverCNCLathe(root = root, modifier = Modifier.fillMaxSize())
                } else {
                    // TODO change with real implementation
                    AppModePickerSample(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
private fun AppModePickerSample(modifier: Modifier = Modifier) {
    val showRemotePicker = remember { mutableStateOf(false) }

    Surface {
        if (showRemotePicker.value) {
            RemoteHostPicker(onHostPick = {}, modifier = modifier)
        } else {
            AppModePicker(
                onLocalClick = {},
                onRemoteClick = { showRemotePicker.value = true },
                modifier = modifier
            )
        }
    }
}

@Composable
private fun createRootComponent(): RootComponent {
    val di = localDI()
    return remember { DefaultRootComponent(di = di, componentContext = di.direct.instance()) }
}
