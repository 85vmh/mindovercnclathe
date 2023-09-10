package com.mindovercnc.linuxcnc.screen

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseScreenModel<S>(initialState: S, componentContext: ComponentContext) {

    protected val mutableState = MutableStateFlow(initialState)
    val state = mutableState.asStateFlow()

    protected val coroutineScope = componentContext.coroutineScope()
}
