package com.mindovercnc.linuxcnc.screen.tools.list.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.CuttingInsertsToolsTab
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.HoldersToolsTab
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.LatheToolsTab
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.ToolsTabItem
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsRootComponent

@Composable
internal fun ToolsListFab(
    currentTab: ToolsTabItem,
    rootComponent: ToolsRootComponent,
    modifier: Modifier = Modifier
) {
    val onClick: () -> Unit =
        when (currentTab) {
            is HoldersToolsTab -> {
                rootComponent::addToolHolder
            }
            is LatheToolsTab -> {
                rootComponent::addLatheTool
            }
            is CuttingInsertsToolsTab -> {
                rootComponent::addCuttingInsert
            }
        }
    val title = currentTab.fabTitle()
    ExtendedFloatingActionButton(
        text = { Text(title) },
        onClick = onClick,
        icon = { Icon(Icons.Default.Add, contentDescription = null) },
        modifier = modifier
    )
}

private fun ToolsTabItem.fabTitle() =
    when (this) {
        is CuttingInsertsToolsTab -> "New Insert"
        is HoldersToolsTab -> "New Holder"
        is LatheToolsTab -> "New Tool"
    }
