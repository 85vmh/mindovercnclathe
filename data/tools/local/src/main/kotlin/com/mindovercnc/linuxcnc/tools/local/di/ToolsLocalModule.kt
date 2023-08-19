package com.mindovercnc.linuxcnc.tools.local.di

import com.mindovercnc.linuxcnc.tools.*
import com.mindovercnc.linuxcnc.tools.local.*
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val ToolsLocalModule = DI.Module("tools_local") {
    bindSingleton<ToolHolderRepository> { ToolHolderRepositoryLocal() }
    bindSingleton<CuttingInsertsRepository> { CuttingInsertsRepositoryLocal() }
    bindSingleton<LatheToolsRepository> { LatheToolsRepositoryLocal() }
    bindSingleton<WorkpieceMaterialRepository> { WorkpieceMaterialRepositoryLocal() }
}