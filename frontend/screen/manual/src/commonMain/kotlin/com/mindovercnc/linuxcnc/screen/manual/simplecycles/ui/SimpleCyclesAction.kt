package com.mindovercnc.linuxcnc.screen.manual.simplecycles.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import com.mindovercnc.linuxcnc.screen.manual.root.ManualRootComponent
import com.mindovercnc.linuxcnc.screen.manual.simplecycles.SimpleCyclesComponent

@Composable
fun RowScope.SimpleCyclesAction(
    rootComponent: ManualRootComponent,
    component: SimpleCyclesComponent
) {
    IconButton(
        onClick = {
            component.applyChanges()
            rootComponent.navigateUp()
        }
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
        )
    }
}
