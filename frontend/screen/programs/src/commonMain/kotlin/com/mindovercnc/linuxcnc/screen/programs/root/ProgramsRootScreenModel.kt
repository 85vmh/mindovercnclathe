package com.mindovercnc.linuxcnc.screen.programs.root

import cafe.adriel.voyager.core.model.ScreenModel
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.mindovercnc.linuxcnc.screen.programs.picker.ProgramPickerComponent
import com.mindovercnc.linuxcnc.screen.programs.picker.ProgramPickerScreenModel
import com.mindovercnc.linuxcnc.screen.programs.programloaded.ProgramLoadedComponent
import com.mindovercnc.linuxcnc.screen.programs.programloaded.ProgramLoadedScreenModel
import okio.Path
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.subDI

class ProgramsRootScreenModel(
    private val di: DI,
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

    override fun openProgram(path: Path) {
        navigation.push(Config.Loaded(path))
    }

    override fun navigateUp() {
        navigation.pop()
    }

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): ProgramsRootComponent.Child =
        when (config) {
            is Config.Loaded -> {
                ProgramsRootComponent.Child.Loaded(loadedComponent(config.path, componentContext))
            }
            Config.Picker -> ProgramsRootComponent.Child.Picker(pickerComponent(componentContext))
        }

    private fun pickerComponent(componentContext: ComponentContext): ProgramPickerComponent {
        return ProgramPickerScreenModel(di, componentContext)
    }

    private fun loadedComponent(
        path: Path,
        componentContext: ComponentContext
    ): ProgramLoadedComponent {
        val subDi = subDI(di.di) { bindProvider { path } }
        return ProgramLoadedScreenModel(subDi, componentContext)
    }

    sealed interface Config : Parcelable {
        @Parcelize data object Picker : Config

        @Parcelize data class Loaded(val path: Path) : Config
    }
}
