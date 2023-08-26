package app

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.mindovercnc.linuxcnc.screen.root.RootChild
import com.mindovercnc.linuxcnc.screen.root.RootComponent
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.orEmpty
import org.jetbrains.compose.resources.rememberImageVector
import org.jetbrains.compose.resources.resource
import ui.bottomBarColor

private val iconButtonModifier = Modifier.size(48.dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewNavigation(root: RootComponent, modifier: Modifier = Modifier) {
    val childStack by root.childStack.subscribeAsState()
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { childStack.active.instance.Title(Modifier) },
                navigationIcon = {
                    if (childStack.items.size > 1) {
                        IconButton(modifier = iconButtonModifier, onClick = root::navigateBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "")
                        }
                    }
                },
                modifier = Modifier.shadow(3.dp)
            )
        },
        bottomBar = {
            // TODO
            AppBottomBar(
                modifier = Modifier.height(60.dp),
                enabled = true, // todo uiState.isBottomBarEnabled,
                selected = childStack.active.instance,
                onClick = root::openTab
            )
        },
        floatingActionButton = { childStack.active.instance.Fab(Modifier) },
    ) { padding ->
        Children(
            stack = childStack,
            modifier = Modifier.padding(padding),
        ) {
            it.instance.Content(Modifier.fillMaxSize())
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
    selected: RootChild,
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
    NavigationBarItem(
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
            if (badgeValue != null) {
                BadgedBox(
                    badge = {
                        Badge(containerColor = MaterialTheme.colorScheme.secondary) {
                            Text(text = badgeValue, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                ) {
                    BottomIcon(tab = tab, tint = tabColor)
                }
            } else {
                BottomIcon(tab = tab, tint = tabColor)
            }
        },
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun BottomIcon(tab: RootComponent.Config, tint: Color, modifier: Modifier = Modifier) {
    val imageVector =
        when (tab) {
            RootComponent.Config.Conversational -> Icons.Default.Star
            RootComponent.Config.Manual -> {
                resource("manual_tab.xml").rememberImageVector(LocalDensity.current).orEmpty()
            }
            RootComponent.Config.Programs -> Icons.Default.List
            RootComponent.Config.Status -> Icons.Default.Info
            RootComponent.Config.Tools -> Icons.Default.Build
        }
    Icon(imageVector = imageVector, contentDescription = null, modifier = modifier, tint = tint)
}
