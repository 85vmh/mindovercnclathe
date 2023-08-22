package com.mindovercnc.linuxcnc.screen

import cafe.adriel.voyager.core.model.StateScreenModel
import com.arkivanov.decompose.ComponentContext

abstract class BaseScreenModel<S>(initialState: S, componentContext: ComponentContext) :
    StateScreenModel<S>(initialState) {
    protected val coroutineScope = componentContext.coroutineScope()
}
