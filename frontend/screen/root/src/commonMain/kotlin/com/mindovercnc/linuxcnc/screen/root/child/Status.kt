package com.mindovercnc.linuxcnc.screen.root.child

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.screen.root.RootComponent
import com.mindovercnc.linuxcnc.screen.status.root.StatusRootComponent
import com.mindovercnc.linuxcnc.screen.status.root.ui.StatusRootScreenUi

class Status(val component: StatusRootComponent) : RootChild(RootComponent.Config.Status) {
    @Composable
    override fun Content(modifier: Modifier) {
        StatusRootScreenUi(component = component, modifier = modifier)
    }

    @Composable
    override fun Title(modifier: Modifier) {
        Text("Status")
    }
}
