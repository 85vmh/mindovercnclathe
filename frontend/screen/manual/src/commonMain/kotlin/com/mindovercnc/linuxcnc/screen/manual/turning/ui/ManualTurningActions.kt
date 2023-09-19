package com.mindovercnc.linuxcnc.screen.manual.turning.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.screen.manual.turning.ManualTurningComponent
import com.mindovercnc.linuxcnc.screen.manual.turning.ManualTurningState
import com.mindovercnc.model.WcsUiModel

@Composable
fun RowScope.ManualTurningActions(component: ManualTurningComponent) {
    val state by component.state.collectAsState()

    val iconColor =
        when {
            state.virtualLimitsUiModel != null -> Color.Green
            state.virtualLimitsAvailable.not() -> Color.LightGray
            else -> LocalContentColor.current
        }

    state.wcsUiModel?.let { uiModel ->
        WcsAction(uiModel, onClick = { component.setWcsSheetVisible(true) })
    }
    DrawerAction(component, iconColor)
    VirtualLimitsAction(state = state, component = component, iconColor = iconColor)
}

@Composable
private fun DrawerAction(component: ManualTurningComponent, iconColor: Color) {
    IconButton(onClick = { component.setSimpleCyclesDrawerOpen(true) }) {
        Icon(
            tint = iconColor,
            imageVector = Icons.Default.Menu,
            contentDescription = null,
        )
    }
}

@Composable
private fun VirtualLimitsAction(
    state: ManualTurningState,
    component: ManualTurningComponent,
    iconColor: Color,
    modifier: Modifier = Modifier
) {
    IconButton(
        enabled = state.virtualLimitsAvailable,
        onClick = { component.setVirtualLimitsActive(state.virtualLimitsUiModel == null) },
        modifier = modifier
    ) {
        Icon(
            tint = iconColor,
            imageVector = Icons.Default.Star,
            contentDescription = null,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WcsAction(
    uiModel: WcsUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BadgedBox(
        badge = {
            Badge(
                modifier = Modifier.background(Color.Black).align(Alignment.Center).padding(16.dp)
            ) {
                Text(text = uiModel.activeOffset)
            }
        },
        modifier = modifier
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.Default.Face,
                contentDescription = null,
            )
        }
    }
}
