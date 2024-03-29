package com.mindovercnc.linuxcnc.screen.manual.turning.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.numpad.InputDialogView
import com.mindovercnc.linuxcnc.screen.manual.root.ManualRootComponent
import com.mindovercnc.linuxcnc.screen.manual.turning.ManualTurningComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManualTurningScreenUi(
    rootComponent: ManualRootComponent,
    component: ManualTurningComponent,
    modifier: Modifier = Modifier
) {
    val state by component.state.collectAsState()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    LaunchedEffect(state.simpleCyclesDrawerOpen) {
        if (state.simpleCyclesDrawerOpen) {
            drawerState.open()
        } else {
            drawerState.close()
        }
    }

    LaunchedEffect(drawerState.isOpen) { component.setSimpleCyclesDrawerOpen(drawerState.isOpen) }

    ModalNavigationDrawer(
        drawerContent = { ManualTurningDrawerContent(rootComponent, component) },
        drawerState = drawerState,
        modifier = modifier
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ManualTurningHeader(
                rootComponent = rootComponent,
                component = component,
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
            ManualTurningFooter(
                rootComponent = rootComponent,
                component = component,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }

    if (state.showWcsSheet) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(
            modifier = Modifier.fillMaxSize(),
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            onDismissRequest = { component.setWcsSheetVisible(false) }
        ) {
            ManualTurningWcsSheet(sheetState, component)
        }
    }

    state.numPadState?.let { numPadState ->
        InputDialogView(
            numPadState = numPadState,
            onCancel = { component.closeNumPad() },
            onSubmit = { value ->
                numPadState.onSubmitAction(value)
                component.closeNumPad()
            }
        )
    }
}
