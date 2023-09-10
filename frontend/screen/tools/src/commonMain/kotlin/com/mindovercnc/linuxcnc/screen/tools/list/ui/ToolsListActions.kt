package com.mindovercnc.linuxcnc.screen.tools.list.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import com.mindovercnc.linuxcnc.screen.tools.add.AddEditItemComponent
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsRootComponent

@Composable
fun RowScope.AddEditItemActions(
    component: AddEditItemComponent<*>,
    rootComponent: ToolsRootComponent,
) {
    IconButton(onClick = component::applyChanges) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
        )
    }
}
