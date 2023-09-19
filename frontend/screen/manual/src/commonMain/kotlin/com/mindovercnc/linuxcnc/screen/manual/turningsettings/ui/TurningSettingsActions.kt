package com.mindovercnc.linuxcnc.screen.manual.turningsettings.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import com.mindovercnc.linuxcnc.screen.manual.root.ManualRootComponent
import com.mindovercnc.linuxcnc.screen.manual.turningsettings.TurningSettingsComponent

@Composable
fun RowScope.TurningSettingsActions(
    rootComponent: ManualRootComponent,
    component: TurningSettingsComponent
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
