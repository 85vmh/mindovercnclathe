package startup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import initializer.Initializer
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import org.kodein.di.compose.rememberInstance

@Composable
fun StartupWindow(onInitialise: () -> Unit) {
    val windowState = rememberWindowState(size = DpSize(320.dp, 240.dp))
    Window(onCloseRequest = {}, state = windowState, undecorated = true) {
        val initializer: Initializer by rememberInstance("app")
        LaunchedEffect(Unit) {
            initializer.initialise()

            async { delay(1000L) }.join()

            onInitialise()
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Initialising")
            CircularProgressIndicator()
        }
    }
}
