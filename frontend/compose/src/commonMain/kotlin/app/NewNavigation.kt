package app

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.mindovercnc.linuxcnc.screen.conversational.ui.ConversationalScreenUi
import com.mindovercnc.linuxcnc.screen.programs.root.ui.ProgramsRootScreenUi
import com.mindovercnc.linuxcnc.screen.status.root.ui.StatusRootScreenUi
import ui.bottomBarColor

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
                selected = childStack.active.instance.config,
                onClick = { root.openTab(it) }
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

private val tabs =
    listOf(
        RootComponent.Config.Manual,
        RootComponent.Config.Conversational,
        RootComponent.Config.Programs,
        RootComponent.Config.Tools,
        RootComponent.Config.Status,
    )

@Composable
private fun AppBottomBar(
    selected: RootComponent.Config,
    onClick: (RootComponent.Config) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    badgeValue: ((RootComponent.Config) -> String?) = { null },
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
    ) {
        tabs.forEach { tab ->
            TabNavigationItem(
                tab = tab,
                badgeValue = badgeValue.invoke(tab),
                enabled = enabled,
                selected = tab == selected,
                onClick = onClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RowScope.TabNavigationItem(
    tab: RootComponent.Config,
    badgeValue: String? = null,
    enabled: Boolean,
    selected: Boolean,
    onClick: (RootComponent.Config) -> Unit
) {
    val tabColor = bottomBarColor(selected, enabled)
    BottomNavigationItem(
        label = {
            Text(
                color = tabColor,
                text = tab.toString(),
            )
        },
        enabled = enabled,
        selected = selected,
        onClick = { onClick(tab) },
        icon = {
//            if (badgeValue != null) {
//                BadgedBox(
//                    badge = {
//                        Badge(containerColor = MaterialTheme.colorScheme.secondary) {
//                            Text(text = badgeValue, style = MaterialTheme.typography.bodyMedium)
//                        }
//                    }
//                ) {
//                    Icon(painter = tab.options.icon!!, contentDescription = "", tint = tabColor)
//                }
//            } else {
//                Icon(painter = tab.options.icon!!, contentDescription = "", tint = tabColor)
//            }
        },
    )
}
