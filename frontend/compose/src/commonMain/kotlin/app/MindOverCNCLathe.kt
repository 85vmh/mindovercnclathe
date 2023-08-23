import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.NewNavigation
import app.RootComponent
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import ui.tab.ManualTab

@Composable
fun MindOverCNCLathe(root: RootComponent? = null, modifier: Modifier = Modifier) {
    if (root != null) {
        NewNavigation(root, modifier)
    } else {
        TabNavigator(ManualTab) { CurrentTab() }
    }
}
