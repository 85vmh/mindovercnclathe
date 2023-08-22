package com.mindovercnc.linuxcnc.screen.tools.root.tabs.lathetool

import com.mindovercnc.linuxcnc.screen.tools.root.ui.LatheToolDeleteModel
import com.mindovercnc.linuxcnc.tools.model.LatheTool

data class LatheToolsState(
    val latheTools: List<LatheTool> = emptyList(),
    val latheToolDeleteModel: LatheToolDeleteModel? = null,
)
