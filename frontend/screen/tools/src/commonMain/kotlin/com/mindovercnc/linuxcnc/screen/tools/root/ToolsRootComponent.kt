package com.mindovercnc.linuxcnc.screen.tools.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.mindovercnc.linuxcnc.screen.tools.root.child.ToolsChild
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert
import com.mindovercnc.linuxcnc.tools.model.LatheTool
import com.mindovercnc.linuxcnc.tools.model.ToolHolder
import com.mindovercnc.model.FeedsAndSpeeds

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

    sealed interface Config : Parcelable {
        @Parcelize data object List : Config
        @Parcelize data class AddEditLatheTool(val latheTool: LatheTool?) : Config
        @Parcelize data class AddEditCuttingInsert(val cuttingInsert: CuttingInsert?) : Config
        @Parcelize data class AddEditToolHolder(val toolHolder: ToolHolder?) : Config
        @Parcelize data class AddEditFeedsAndSpeeds(val feedsAndSpeeds: FeedsAndSpeeds?) : Config
    }
}
