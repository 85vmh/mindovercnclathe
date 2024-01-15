import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import app.AppModePicker
import app.RemoteHostPicker
import startup.args.StartupArgs
import themes.AppTheme

@Composable
fun ApplicationScope.CommunicationPickerWindow(
    startupArgs: StartupArgs,
    modifier: Modifier = Modifier, onCommunication: (Communication) -> Unit,
) {
    Window(
        onCloseRequest = this::exitApplication,
        title = "KtCnc",
        focusable = false,
        undecorated = !startupArgs.topBarEnabled.enabled
    ) {
        val showRemotePicker = remember { mutableStateOf(false) }

        AppTheme(startupArgs.darkMode) {
            Surface {
                if (showRemotePicker.value) {
                    RemoteHostPicker(
                        onHostPick = { host ->
                            onCommunication(Communication.Remote(host))
                        },
                        modifier = modifier,
                    )
                } else {
                    AppModePicker(
                        onLocalClick = { onCommunication(Communication.Local) },
                        onRemoteClick = { showRemotePicker.value = true },
                        modifier = modifier
                    )
                }
            }
        }
    }
}