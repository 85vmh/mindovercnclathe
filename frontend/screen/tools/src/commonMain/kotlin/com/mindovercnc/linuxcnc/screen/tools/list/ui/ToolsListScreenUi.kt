package com.mindovercnc.linuxcnc.screen.tools.list.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.mindovercnc.linuxcnc.screen.tools.list.ToolsComponent

private val tabContentModifier = Modifier.fillMaxWidth()

@Composable
fun ToolsListScreenUi(component: ToolsComponent, modifier: Modifier = Modifier) {
    val tabStack by component.childSlot.subscribeAsState()
    Column(modifier) { tabStack.child!!.instance.Content(component, tabContentModifier) }
}
