import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.mindovercnc.linuxcnc.screen.root.RootScreenModel
import di.withAppDi
import org.jetbrains.skiko.wasm.onWasmReady
import org.kodein.di.compose.localDI

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val lifecycle = LifecycleRegistry()
    val componentContext = DefaultComponentContext(lifecycle)
    onWasmReady {
        CanvasBasedWindow("Ktcnc") {
            withAppDi {
                MindOverCNCLathe(
                    RootScreenModel(di = localDI(), componentContext = componentContext)
                )
            }
        }
    }
}
