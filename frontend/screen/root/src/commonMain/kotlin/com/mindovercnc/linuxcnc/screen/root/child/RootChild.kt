package com.mindovercnc.linuxcnc.screen.root.child

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.screen.TitledChild
import com.mindovercnc.linuxcnc.screen.root.RootComponent

sealed class RootChild(val config: RootComponent.Config) : TitledChild {

    @Composable abstract fun Content(modifier: Modifier)

    @Composable open fun Fab(modifier: Modifier) {}

    @Composable open fun NavigationIcon(modifier: Modifier) {}
}
