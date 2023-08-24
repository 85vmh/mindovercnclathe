package com.mindovercnc.linuxcnc.screen.manual.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.mindovercnc.linuxcnc.screen.manual.turning.ManualTurningComponent

interface ManualRootComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class Turning(private val component: ManualTurningComponent) : Child
    }

    sealed interface Config : Parcelable {
        @Parcelize data object Turning : Config
    }
}
