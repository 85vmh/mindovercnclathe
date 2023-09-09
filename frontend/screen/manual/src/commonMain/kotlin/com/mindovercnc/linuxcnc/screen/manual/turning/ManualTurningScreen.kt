package com.mindovercnc.linuxcnc.screen.manual.turning

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mindovercnc.linuxcnc.screen.manual.Manual
import com.mindovercnc.linuxcnc.screen.manual.simplecycles.SimpleCyclesScreen
import com.mindovercnc.linuxcnc.screen.manual.turning.ui.ManualTurningActions
import com.mindovercnc.linuxcnc.screen.manual.turning.ui.ManualTurningScreenUi
import com.mindovercnc.linuxcnc.screen.manual.turning.ui.ManualTurningWcsSheet
import com.mindovercnc.linuxcnc.screen.manual.turning.ui.SimpleCyclesGrid
import com.mindovercnc.linuxcnc.screen.rememberScreenModel
import com.mindovercnc.model.SimpleCycle
import kotlinx.coroutines.launch

class ManualTurningScreen : Manual("Manual Turning") {

    override val drawerEnabled: Boolean
        @Composable get() = true

    @Composable
    override fun ColumnScope.DrawerContent(drawerState: DrawerState) {
        val scope = rememberCoroutineScope()
        val navigator = LocalNavigator.currentOrThrow

    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun SheetContent(sheetState: ModalBottomSheetState) {
        val screenModel = rememberScreenModel<ManualTurningScreenModel>()

        ManualTurningWcsSheet(
            modifier = Modifier.fillMaxWidth(),
            sheetState = sheetState,
            component = screenModel
        )
    }

    @Composable
    override fun RowScope.Actions() {
        val screenModel = rememberScreenModel<ManualTurningScreenModel>()
        ManualTurningActions(screenModel)
    }

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel<ManualTurningScreenModel>()
        ManualTurningScreenUi(TODO(), screenModel)
    }
}
