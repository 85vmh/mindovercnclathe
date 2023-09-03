package com.mindovercnc.linuxcnc.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface TitledChild {
    @Composable fun Title(modifier: Modifier)
}
