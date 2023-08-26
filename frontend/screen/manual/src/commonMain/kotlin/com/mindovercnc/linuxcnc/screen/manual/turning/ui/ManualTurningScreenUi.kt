package com.mindovercnc.linuxcnc.screen.manual.turning.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.numpad.InputDialogView
import com.mindovercnc.linuxcnc.screen.manual.root.ManualRootComponent
import com.mindovercnc.linuxcnc.screen.manual.turning.ManualTurningComponent

@Composable
fun ManualTurningScreenUi(
    rootComponent: ManualRootComponent,
    component: ManualTurningComponent,
    modifier: Modifier = Modifier
) {
    val state by component.state.collectAsState()

    ManualTurningContent(rootComponent, component, state, modifier)

    state.numPadState?.let { numPadState ->
        InputDialogView(
            numPadState = numPadState,
            onCancel = { component.closeNumPad() },
            onSubmit = {
                numPadState.onSubmitAction(it)
                component.closeNumPad()
            }
        )
    }
}
