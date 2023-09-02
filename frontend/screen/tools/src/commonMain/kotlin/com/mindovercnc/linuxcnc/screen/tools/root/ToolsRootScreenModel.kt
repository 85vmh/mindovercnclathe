package com.mindovercnc.linuxcnc.screen.tools.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.mindovercnc.linuxcnc.screen.tools.list.ToolsComponent
import com.mindovercnc.linuxcnc.screen.tools.list.ToolsScreenModel
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
import org.kodein.di.direct
import org.kodein.di.instance

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
            Config.List -> Child.List(toolsComponent())
            is Config.AddEditCuttingInsert -> {
                Child.AddEditCuttingInsert(addEditCuttingInsertComponent())
            }
            is Config.AddEditLatheTool -> Child.AddEditLatheTool(addEditLatheToolComponent())
            is Config.AddEditToolHolder -> Child.AddEditToolHolder(addEditToolHolderComponent())
        }
    }

    private fun toolsComponent(): ToolsComponent {
        return di.direct.instance<ToolsScreenModel>()
    }

    private fun addEditCuttingInsertComponent(): AddEditCuttingInsertComponent {
        return di.direct.instance<AddEditCuttingInsertScreenModel>()
    }

    private fun addEditLatheToolComponent(): AddEditLatheToolComponent {
        return di.direct.instance<AddEditLatheToolScreenModel>()
    }

    private fun addEditToolHolderComponent(): AddEditToolHolderComponent {
        return di.direct.instance<AddEditToolHolderScreenModel>()
    }
}
