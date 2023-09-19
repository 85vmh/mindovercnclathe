package com.mindovercnc.linuxcnc.screen.manual.tapersettings.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.screen.manual.root.ManualRootComponent
import com.mindovercnc.linuxcnc.screen.manual.tapersettings.TaperSettingsComponent

@Composable
fun RowScope.TaperSettingsActions(
    rootComponent: ManualRootComponent,
    component: TaperSettingsComponent,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = {
            component.applyChanges()
            rootComponent.navigateUp()
        },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
        )
    }
}
