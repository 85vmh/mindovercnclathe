package app

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.mindovercnc.linuxcnc.screen.conversational.ui.ConversationalScreenUi
import com.mindovercnc.linuxcnc.screen.programs.root.ui.ProgramsRootScreenUi
import com.mindovercnc.linuxcnc.screen.status.root.ui.StatusRootScreenUi
import ui.AppBottomBar
import ui.tab.StatusTab

@Composable
fun NewNavigation(root: RootComponent, modifier: Modifier = Modifier) {
    val childStack by root.childStack.subscribeAsState()
    Scaffold(
        modifier = modifier,
        topBar = {
            // TODO
        },
        bottomBar = {
            // TODO
            AppBottomBar(
                modifier = Modifier.height(60.dp),
                enabled = true, // todo uiState.isBottomBarEnabled,
                selected = StatusTab,
                onClick = {
                    // TODO:
                }
            )
        }
    ) { padding ->
        val childModifier = Modifier.padding(padding)
        Children(childStack) {
            when (val child = it.instance) {
                is RootComponent.Child.Conversational -> {
                    ConversationalScreenUi(modifier = childModifier)
                }
                is RootComponent.Child.Manual -> {
                    TODO()
                }
                is RootComponent.Child.Programs -> {
                    ProgramsRootScreenUi(child.component, modifier = childModifier)
                }
                is RootComponent.Child.Status -> {
                    StatusRootScreenUi(component = child.component, modifier = childModifier)
                }
                is RootComponent.Child.Tools -> TODO()
            }
        }
    }
}