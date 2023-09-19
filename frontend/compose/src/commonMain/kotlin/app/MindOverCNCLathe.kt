import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import app.NewNavigation
import com.arkivanov.decompose.ComponentContext
import com.mindovercnc.linuxcnc.screen.root.RootComponent
import com.mindovercnc.linuxcnc.screen.root.RootScreenModel
import org.kodein.di.compose.localDI

@Composable
fun MindOverCNCLathe(root: RootComponent, modifier: Modifier = Modifier) {
    NewNavigation(root, modifier)
}

@Composable
fun createRootComponent(componentContext: ComponentContext): RootComponent {
    val di = localDI()
    return remember { RootScreenModel(di = di, componentContext = componentContext) }
}
