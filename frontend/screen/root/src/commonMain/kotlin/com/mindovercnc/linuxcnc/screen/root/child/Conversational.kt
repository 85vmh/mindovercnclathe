package com.mindovercnc.linuxcnc.screen.root.child

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.screen.conversational.ConversationalComponent
import com.mindovercnc.linuxcnc.screen.conversational.ui.ConversationalFab
import com.mindovercnc.linuxcnc.screen.conversational.ui.ConversationalScreenUi
import com.mindovercnc.linuxcnc.screen.root.RootComponent

class Conversational(val component: ConversationalComponent) :
    RootChild(RootComponent.Config.Conversational) {
    @Composable
    override fun Content(modifier: Modifier) {
        ConversationalScreenUi(modifier = modifier)
    }

    @Composable
    override fun Fab(modifier: Modifier) {
        ConversationalFab(onClick = {})
    }

    @Composable
    override fun Title(modifier: Modifier) {
        Text("Conversational")
    }
}
