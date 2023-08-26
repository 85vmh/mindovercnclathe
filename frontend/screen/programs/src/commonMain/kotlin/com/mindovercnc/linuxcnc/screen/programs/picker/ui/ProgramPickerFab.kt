package com.mindovercnc.linuxcnc.screen.programs.picker.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import com.mindovercnc.linuxcnc.screen.programs.picker.ProgramPickerComponent
import com.mindovercnc.linuxcnc.screen.programs.programloaded.ProgramLoadedScreen

@Composable
fun ProgramPickerFab(component: ProgramPickerComponent, modifier: Modifier = Modifier) {
    val state by component.state.collectAsState()
    val navigator = LocalNavigator.current

    state.editor?.let { editor ->
        ExtendedFloatingActionButton(
            text = { Text("Load Program") },
            onClick = {
                navigator?.push(ProgramLoadedScreen(editor.file))
                // TODO: add decompose navigation
            },
            icon = {
                Icon(
                    Icons.Default.ExitToApp,
                    contentDescription = "",
                )
            },
            modifier = modifier
        )
    }
}
