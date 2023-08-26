package com.mindovercnc.linuxcnc.screen.manual.turningsettings

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mindovercnc.linuxcnc.screen.manual.Manual
import com.mindovercnc.linuxcnc.screen.manual.turningsettings.ui.TurningSettingsScreenUi
import com.mindovercnc.linuxcnc.screen.rememberScreenModel

class TurningSettingsScreen : Manual("Turning Settings") {

    @Composable
    override fun RowScope.Actions() {
        val screenModel = rememberScreenModel<TurningSettingsScreenModel>()
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
        val screenModel = rememberScreenModel<TurningSettingsScreenModel>()
        TurningSettingsScreenUi(screenModel, Modifier.fillMaxSize())
    }
}
