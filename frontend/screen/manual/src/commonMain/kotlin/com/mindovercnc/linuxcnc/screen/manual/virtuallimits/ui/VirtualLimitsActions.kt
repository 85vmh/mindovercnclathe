package com.mindovercnc.linuxcnc.screen.manual.virtuallimits.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import com.mindovercnc.linuxcnc.screen.manual.root.ManualRootComponent
import com.mindovercnc.linuxcnc.screen.manual.virtuallimits.VirtualLimitsComponent

@Composable
fun RowScope.VirtualLimitsActions(
    rootComponent: ManualRootComponent,
    component: VirtualLimitsComponent
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
