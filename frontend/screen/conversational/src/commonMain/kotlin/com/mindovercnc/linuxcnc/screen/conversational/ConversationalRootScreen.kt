package com.mindovercnc.linuxcnc.screen.conversational

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.screen.conversational.ui.ConversationalFab
import com.mindovercnc.linuxcnc.screen.conversational.ui.ConversationalScreenUi

class ConversationalRootScreen : Conversational("Conversational") {

    @Composable
    override fun Fab() {
        ConversationalFab(onClick = {})
    }

    @Composable
    override fun Content() {
        ConversationalScreenUi(Modifier.fillMaxSize())
    }
}
