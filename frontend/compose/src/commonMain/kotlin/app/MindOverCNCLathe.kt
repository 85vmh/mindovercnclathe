import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.RootComponent
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.mindovercnc.linuxcnc.screen.status.root.ui.StatusRootScreenUi
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
private fun NewNavigation(root: RootComponent, modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier) { padding ->
        val childModifier = Modifier.padding(padding)
        Children(root.childStack) {
            when (val child = it.instance) {
                is RootComponent.Child.Conversational -> {
                    TODO()
                }
                is RootComponent.Child.Manual -> {
                    TODO()
                }
                is RootComponent.Child.Programs -> TODO()
                is RootComponent.Child.Status -> {
                    StatusRootScreenUi(component = child.component, modifier = childModifier)
                }
                is RootComponent.Child.Tools -> TODO()
            }
        }
    }
}
