package com.mindovercnc.linuxcnc.screen.tools.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.mindovercnc.linuxcnc.screen.rememberScreenModel
import com.mindovercnc.linuxcnc.screen.tools.Tools
import com.mindovercnc.linuxcnc.screen.tools.list.ui.ToolTabsView
import com.mindovercnc.linuxcnc.screen.tools.list.ui.ToolsListFab
import com.mindovercnc.linuxcnc.screen.tools.list.ui.ToolsListScreenUi

class ToolsRootScreen : Tools() {

    @Composable
    override fun Title() {
        val screenModel = rememberScreenModel<ToolsListScreenModel>()
        val childSlot by screenModel.childSlot.subscribeAsState()

        ToolTabsView(
            modifier = Modifier.width(450.dp).height(48.dp),
            currentTab = childSlot.child!!.instance.config,
            onTabSelected = screenModel::selectTab
        )
    }

    @Composable
    override fun Fab() {
        val screenModel = rememberScreenModel<ToolsListScreenModel>()
        val childSlot by screenModel.childSlot.subscribeAsState()

        ToolsListFab(childSlot.child!!.instance, null)
    }

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel<ToolsListScreenModel>()
        ToolsListScreenUi(TODO(), screenModel, Modifier.fillMaxSize())
    }
}
