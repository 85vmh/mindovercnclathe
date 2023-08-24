package com.mindovercnc.linuxcnc.screen.manual.turning.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mindovercnc.linuxcnc.numpad.InputDialogView
import com.mindovercnc.linuxcnc.screen.manual.turning.ManualTurningComponent

@Composable
fun ManualTurningScreenUi(screenModel: ManualTurningComponent) {
    val state by screenModel.state.collectAsState()
    val navigator = LocalNavigator.currentOrThrow

    ManualTurningContent(screenModel, state, navigator)

    state.numPadState?.let { numPadState ->
        InputDialogView(
            numPadState = numPadState,
            onCancel = { screenModel.closeNumPad() },
            onSubmit = {
                numPadState.onSubmitAction(it)
                screenModel.closeNumPad()
            }
        )
    }
}
