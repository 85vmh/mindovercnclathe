package com.mindovercnc.linuxcnc.screen.manual.virtuallimits

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mindovercnc.linuxcnc.screen.manual.Manual
import com.mindovercnc.linuxcnc.screen.manual.virtuallimits.ui.VirtualLimitsScreenUi
import com.mindovercnc.linuxcnc.screen.rememberScreenModel

class VirtualLimitsScreen : Manual("Virtual Limits") {

    @Composable
    override fun RowScope.Actions() {
        val screenModel = rememberScreenModel<VirtualLimitsScreenModel>()
        val navigator = LocalNavigator.currentOrThrow
        IconButton(
            modifier = iconButtonModifier,
            onClick = {
                screenModel.applyChanges()
                navigator.pop()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "",
            )
        }
    }

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel<VirtualLimitsScreenModel>()
        VirtualLimitsScreenUi(screenModel)
    }
}
