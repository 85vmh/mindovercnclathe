package com.mindovercnc.linuxcnc.screen.tools.root.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.cuttinginsert.ui.AddEditCuttingInsertScreenUi
import com.mindovercnc.linuxcnc.screen.tools.list.ui.ToolsListScreenUi
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsRootComponent

@Composable
fun ToolsRootScreenUi(
    component: ToolsRootComponent,
    modifier: Modifier = Modifier,
) {
    val childModifier = Modifier.fillMaxSize()
    Children(component.childStack, modifier) {
        when (val child = it.instance) {
            is ToolsRootComponent.Child.AddEditCuttingInsert -> {
                AddEditCuttingInsertScreenUi(child.component, childModifier)
            }
            is ToolsRootComponent.Child.AddEditLatheTool -> {
                // TODO: AddEditLatheToolContent()
            }
            is ToolsRootComponent.Child.AddEditToolHolder -> {
                // TODO:
            }
            is ToolsRootComponent.Child.List -> {
                ToolsListScreenUi(component, child.component, childModifier)
            }
        }
    }
}
