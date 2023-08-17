package com.mindovercnc.linuxcnc.screen.conversational

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

class ConversationalRootScreen : Conversational("Conversational") {

    @Composable
    override fun Fab() {
        ExtendedFloatingActionButton(
            text = { Text("Create New") },
            onClick = {

            },
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

//        Column {
//            Text("Conversational Root")
//        }
    }
}