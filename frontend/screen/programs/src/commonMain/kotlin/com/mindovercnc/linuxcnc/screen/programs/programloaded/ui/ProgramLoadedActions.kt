package com.mindovercnc.linuxcnc.screen.programs.programloaded.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import com.mindovercnc.linuxcnc.screen.programs.programloaded.ProgramLoadedComponent

@Composable
fun RowScope.ProgramLoadedActions(component: ProgramLoadedComponent) {
    IconButton(onClick = component::stopProgram) {
        Icon(imageVector = Icons.Default.Close, contentDescription = null)
    }
    IconButton(onClick = component::runProgram) {
        Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
    }
}
