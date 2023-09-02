package com.mindovercnc.linuxcnc.screen.tools.list.tabs.lathetool

import com.mindovercnc.linuxcnc.screen.AppScreenComponent
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.lathetool.LatheToolsState
import com.mindovercnc.linuxcnc.tools.model.LatheTool

interface LatheToolsComponent : AppScreenComponent<LatheToolsState>{
    fun loadLatheTools()
    fun requestDeleteLatheTool(latheTool: LatheTool)
    fun cancelDeleteLatheTool()
    fun deleteLatheTool(latheTool: LatheTool)

}