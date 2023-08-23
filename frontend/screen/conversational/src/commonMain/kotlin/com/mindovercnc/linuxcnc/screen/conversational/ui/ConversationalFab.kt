package com.mindovercnc.linuxcnc.screen.conversational.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ConversationalFab(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    expanded: Boolean = true
) {
    ExtendedFloatingActionButton(
        text = { Text("Create New") },
        onClick = onClick,
        icon = {
            Icon(
                Icons.Default.Add,
                contentDescription = "",
            )
        },
        expanded = expanded,
        modifier = modifier
    )
}
