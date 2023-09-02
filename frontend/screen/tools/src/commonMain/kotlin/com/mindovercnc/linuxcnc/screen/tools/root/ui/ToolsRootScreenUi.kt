package com.mindovercnc.linuxcnc.screen.tools.root.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.cuttinginsert.ui.AddEditCuttingInsertScreenUi
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsRootComponent

@Composable
fun ToolsRootScreenUi(
    component: ToolsRootComponent,
    modifier: Modifier = Modifier,
) {
    Children(component.childStack, modifier) {
        when (val child = it.instance) {
            is ToolsRootComponent.Child.AddEditCuttingInsert -> {
                AddEditCuttingInsertScreenUi(child.component)
            }
            is ToolsRootComponent.Child.AddEditLatheTool -> {

            }
            is ToolsRootComponent.Child.AddEditToolHolder -> {

            }
            is ToolsRootComponent.Child.List -> {

            }
        }
    }
}
