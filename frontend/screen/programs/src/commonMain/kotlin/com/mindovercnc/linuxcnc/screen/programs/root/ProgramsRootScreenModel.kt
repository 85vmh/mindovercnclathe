package com.mindovercnc.linuxcnc.screen.programs.root

import cafe.adriel.voyager.core.model.ScreenModel
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.mindovercnc.linuxcnc.screen.programs.picker.ProgramPickerComponent
import com.mindovercnc.linuxcnc.screen.programs.picker.ProgramPickerScreenModel
import com.mindovercnc.linuxcnc.screen.programs.programloaded.ProgramLoadedComponent
import com.mindovercnc.linuxcnc.screen.programs.programloaded.ProgramLoadedScreenModel
import okio.Path
import org.kodein.di.*

class ProgramsRootScreenModel(
    private val di: DirectDI,
    componentContext: ComponentContext,
) : ScreenModel, ProgramsRootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private val _childStack =
        childStack(
            source = navigation,
            initialConfiguration = Config.Picker,
            childFactory = ::createChild
        )

    override val childStack: Value<ChildStack<*, ProgramsRootComponent.Child>> = _childStack

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): ProgramsRootComponent.Child =
        when (config) {
            is Config.Loaded -> ProgramsRootComponent.Child.Loaded(loadedComponent(config.path))
            Config.Picker -> ProgramsRootComponent.Child.Picker(pickerComponent())
        }

    private fun pickerComponent(): ProgramPickerComponent {
        return di.instance<ProgramPickerScreenModel>()
    }

    private fun loadedComponent(path: Path): ProgramLoadedComponent {
        val subDi = subDI(di.di) { bindProvider { path } }
        return subDi.direct.instance<ProgramLoadedScreenModel>()
    }

    sealed interface Config : Parcelable {
        @Parcelize data object Picker : Config

        @Parcelize data class Loaded(val path: Path) : Config
    }
}
