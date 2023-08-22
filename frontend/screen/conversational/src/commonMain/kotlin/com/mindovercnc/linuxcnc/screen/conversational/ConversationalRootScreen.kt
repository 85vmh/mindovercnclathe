package com.mindovercnc.linuxcnc.screen.conversational

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

class ConversationalRootScreen : Conversational("Conversational") {

    @Composable
    override fun Fab() {
        ExtendedFloatingActionButton(
            text = { Text("Create New") },
            onClick = {},
            icon = {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "",
                )
            }
        )
    }

    @Composable
    override fun Content() {
        //        val screenModel = rememberScreenModel<ManualRootScreenModel>()
        //        val state by screenModel.state.collectAsState()
        //
        //        when (state) {
        //            is ManualRootScreenModel.State.DrawerOpened -> {}
        //
        //        }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("TODO Conversational Root", style = MaterialTheme.typography.titleLarge)
        }
    }
}
