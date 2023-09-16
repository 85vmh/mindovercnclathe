package com.mindovercnc.linuxcnc.tools.local.di

import com.mindovercnc.linuxcnc.tools.*
import com.mindovercnc.linuxcnc.tools.local.*
import org.kodein.di.DI
import org.kodein.di.bindSingleton

val ToolsLocalModule = DI.Module("tools_local") {
    bindSingleton<ToolHolderRepository> { ToolHolderRepositoryLocal() }
    bindSingleton<CuttingInsertsRepository> { CuttingInsertsRepositoryLocal() }
    bindSingleton<LatheToolRepository> { LatheToolRepositoryLocal() }
    bindSingleton<WorkpieceMaterialRepository> { WorkpieceMaterialRepositoryLocal() }
}