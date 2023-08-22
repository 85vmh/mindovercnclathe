package com.mindovercnc.linuxcnc.screen.status.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.screen.rememberScreenModel
import com.mindovercnc.linuxcnc.screen.status.Status
import com.mindovercnc.linuxcnc.screen.status.root.ui.StatusRootScreenUi

class StatusRootScreen : Status("Status") {

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel<StatusRootScreenModel>()
        StatusRootScreenUi(component = screenModel, Modifier.fillMaxSize())
    }
}
