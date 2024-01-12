package com.mindovercnc.linuxcnc.screen.tools.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.mindovercnc.linuxcnc.screen.tools.root.child.ToolsChild
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert
import com.mindovercnc.linuxcnc.tools.model.LatheTool
import com.mindovercnc.linuxcnc.tools.model.ToolHolder
import com.mindovercnc.model.FeedsAndSpeeds
import kotlinx.serialization.Serializable

interface ToolsRootComponent {
    val childStack: Value<ChildStack<*, ToolsChild>>

    fun addCuttingInsert()

    fun addLatheTool()

    fun addToolHolder()

    fun editCuttingInsert(cuttingInsert: CuttingInsert)

    fun editLatheTool(latheTool: LatheTool)

    fun editToolHolder(toolHolder: ToolHolder)

    fun addFeedsAndSpeeds()

    fun editFeedsAndSpeeds(feedsAndSpeeds: FeedsAndSpeeds)

    fun navigateUp()

    @Serializable
    sealed interface Config {
        @Serializable data object List : Config

        @Serializable data class AddEditLatheTool(val latheTool: LatheTool?) : Config

        @Serializable data class AddEditCuttingInsert(val cuttingInsert: CuttingInsert?) : Config

        @Serializable data class AddEditToolHolder(val toolHolder: ToolHolder?) : Config

        @Serializable
        data class AddEditFeedsAndSpeeds(val feedsAndSpeeds: FeedsAndSpeeds?) : Config
    }
}
