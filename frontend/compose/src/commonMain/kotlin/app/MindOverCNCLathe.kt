import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import app.NewNavigation
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.arkivanov.decompose.ComponentContext
import com.mindovercnc.linuxcnc.screen.root.RootComponent
import com.mindovercnc.linuxcnc.screen.root.RootScreenModel
import org.kodein.di.compose.localDI
import ui.tab.ManualTab

@Composable
fun MindOverCNCLathe(root: RootComponent? = null, modifier: Modifier = Modifier) {
    if (root != null) {
        NewNavigation(root, modifier)
    } else {
        TabNavigator(ManualTab) { CurrentTab() }
    }
}

@Composable
fun createRootComponent(componentContext: ComponentContext): RootComponent {
    val di = localDI()
    return remember { RootScreenModel(di = di, componentContext = componentContext) }
}
