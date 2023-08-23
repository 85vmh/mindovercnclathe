package ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.Tab
import ui.tab.*

private val tabs =
    arrayOf<AppTab<*>>(ManualTab, ConversationalTab, ProgramsTab, ToolsTab, StatusTab)

@Composable
fun AppBottomBar(
    selected: AppTab<*>,
    onClick: (Tab) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    badgeValue: ((AppTab<*>) -> String?) = { null },
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
    tab: Tab,
    badgeValue: String? = null,
    enabled: Boolean,
    selected: Boolean,
    onClick: (Tab) -> Unit
) {
    val tabColor =
        when {
            selected -> MaterialTheme.colorScheme.primary
            !enabled -> MaterialTheme.colorScheme.secondary
            else -> MaterialTheme.colorScheme.onPrimaryContainer
        }

    BottomNavigationItem(
        label = {
            Text(
                color = tabColor,
                text = tab.options.title,
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
                    Icon(painter = tab.options.icon!!, contentDescription = "", tint = tabColor)
                }
            } else {
                Icon(painter = tab.options.icon!!, contentDescription = "", tint = tabColor)
            }
        },
    )

    // TODO: this requires the bottom bar to be high so that it won't overlap
    //        NavigationBarItem(
    //            label = {
    //                Text(
    //                    color = tabColor,
    //                    text = tab.options.title,
    //                )
    //            },
    //            enabled = enabled,
    //            selected = selected,
    //            onClick = { onClick(tab) },
    //            icon = {
    //                if (badgeValue != null) {
    //                    BadgedBox(
    //                        badge = {
    //                            Badge(containerColor = MaterialTheme.colorScheme.secondary) {
    //                                Text(text = badgeValue, style =
    // MaterialTheme.typography.bodyMedium)
    //                            }
    //                        }
    //                    ) {
    //                        Icon(painter = tab.options.icon!!, contentDescription = "", tint =
    // tabColor)
    //                    }
    //                } else {
    //                    Icon(painter = tab.options.icon!!, contentDescription = "", tint =
    // tabColor)
    //                }
    //            },
    //        )
}
