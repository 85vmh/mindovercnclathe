package com.mindovercnc.linuxcnc.screen.tools.list.tabs.lathetool

import com.mindovercnc.linuxcnc.screen.tools.list.ui.LatheToolDeleteModel
import com.mindovercnc.linuxcnc.tools.model.LatheTool

data class LatheToolsState(
    val latheTools: List<LatheTool> = emptyList(),
    val latheToolDeleteModel: LatheToolDeleteModel? = null,
)
