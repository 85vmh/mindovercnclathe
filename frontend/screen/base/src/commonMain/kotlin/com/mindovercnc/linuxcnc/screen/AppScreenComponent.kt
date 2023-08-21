package com.mindovercnc.linuxcnc.screen

import com.mindovercnc.linuxcnc.numpad.data.InputType
import kotlinx.coroutines.flow.StateFlow

interface AppScreenComponent<S> {
    val state: StateFlow<S>
}
