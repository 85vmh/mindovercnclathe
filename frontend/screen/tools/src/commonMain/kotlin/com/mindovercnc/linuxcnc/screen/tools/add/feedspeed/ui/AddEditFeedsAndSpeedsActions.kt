package com.mindovercnc.linuxcnc.screen.tools.add.feedspeed.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import com.mindovercnc.linuxcnc.screen.tools.add.feedspeed.AddEditFeedsAndSpeedsComponent
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsRootComponent

@Composable
fun RowScope.AddEditFeedsAndSpeedsActions(
    rootComponent: ToolsRootComponent,
    component: AddEditFeedsAndSpeedsComponent
) {
    IconButton(
        onClick = {
            component.applyChanges()
            rootComponent.navigateUp()
        }
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "",
        )
    }
}
