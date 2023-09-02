package com.mindovercnc.linuxcnc.screen.tools.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.mindovercnc.linuxcnc.screen.tools.list.ToolsListComponent
import com.mindovercnc.linuxcnc.screen.tools.list.ToolsListScreenModel
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.cuttinginsert.add.AddEditCuttingInsertComponent
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.cuttinginsert.add.AddEditCuttingInsertScreenModel
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.lathetool.add.AddEditLatheToolComponent
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.lathetool.add.AddEditLatheToolScreenModel
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.toolholder.add.AddEditToolHolderComponent
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.toolholder.add.AddEditToolHolderScreenModel
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsRootComponent.Child
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsRootComponent.Config
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert
import com.mindovercnc.linuxcnc.tools.model.LatheTool
import com.mindovercnc.linuxcnc.tools.model.ToolHolder
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.subDI

class ToolsRootScreenModel(
    private val di: DI,
    componentContext: ComponentContext,
) : ToolsRootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private val _childStack =
        childStack(
            source = navigation,
            initialConfiguration = Config.List,
            childFactory = ::createChild
        )

    override val childStack: Value<ChildStack<*, Child>> = _childStack

    override fun addCuttingInsert() {
        navigation.push(Config.AddEditCuttingInsert(null))
    }

    override fun addLatheTool() {
        navigation.push(Config.AddEditLatheTool(null))
    }

    override fun addToolHolder() {
        navigation.push(Config.AddEditToolHolder(null))
    }

    override fun editCuttingInsert(cuttingInsert: CuttingInsert) {
        navigation.push(Config.AddEditCuttingInsert(cuttingInsert))
    }

    override fun editLatheTool(latheTool: LatheTool) {
        navigation.push(Config.AddEditLatheTool(latheTool))
    }

    override fun editToolHolder(toolHolder: ToolHolder) {
        navigation.push(Config.AddEditToolHolder(toolHolder))
    }

    override fun navigateUp() {
        navigation.pop()
    }

    private fun createChild(config: Config, componentContext: ComponentContext): Child {
        return when (config) {
            Config.List -> {
                Child.List(toolsComponent(componentContext))
            }
            is Config.AddEditCuttingInsert -> {
                Child.AddEditCuttingInsert(
                    addEditCuttingInsertComponent(config.cuttingInsert, componentContext)
                )
            }
            is Config.AddEditLatheTool -> {
                Child.AddEditLatheTool(
                    addEditLatheToolComponent(config.latheTool, componentContext)
                )
            }
            is Config.AddEditToolHolder -> {
                Child.AddEditToolHolder(
                    addEditToolHolderComponent(config.toolHolder, componentContext)
                )
            }
        }
    }

    private fun toolsComponent(componentContext: ComponentContext): ToolsListComponent {
        return ToolsListScreenModel(di, componentContext)
    }

    private fun addEditCuttingInsertComponent(
        cuttingInsert: CuttingInsert?,
        componentContext: ComponentContext
    ): AddEditCuttingInsertComponent {
        val subDI = subDI(di) { if (cuttingInsert != null) bindSingleton { cuttingInsert } }
        return AddEditCuttingInsertScreenModel(subDI, componentContext)
    }

    private fun addEditLatheToolComponent(
        latheTool: LatheTool?,
        componentContext: ComponentContext
    ): AddEditLatheToolComponent {
        val subDI = subDI(di) { if (latheTool != null) bindSingleton { latheTool } }
        return AddEditLatheToolScreenModel(subDI, componentContext)
    }

    private fun addEditToolHolderComponent(
        toolHolder: ToolHolder?,
        componentContext: ComponentContext
    ): AddEditToolHolderComponent {
        val subDI = subDI(di) { if (toolHolder != null) bindSingleton { toolHolder } }
        return AddEditToolHolderScreenModel(subDI, componentContext)
    }
}
