package com.mindovercnc.linuxcnc.screen.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.mindovercnc.linuxcnc.screen.root.child.RootChild

interface RootComponent : BackHandlerOwner {
    val childStack: Value<ChildStack<*, RootChild>>

    fun openTab(tab: Config)

    sealed interface Config : Parcelable {
        @Parcelize data object Manual : Config
        @Parcelize data object Conversational : Config
        @Parcelize data object Programs : Config
        @Parcelize data object Tools : Config
        @Parcelize data object Status : Config
    }
}
