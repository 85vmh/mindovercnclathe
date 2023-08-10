package com.mindovercnc.linuxcnc.tools.remote.di

import com.mindovercnc.linuxcnc.tools.*
import com.mindovercnc.linuxcnc.tools.remote.*
import org.kodein.di.DI
import org.kodein.di.bindSingleton

val ToolsRemoteModule = DI.Module("tools_remote") {
    bindSingleton<ToolHolderRepository> { ToolHolderRepositoryRemote() }
    bindSingleton<CuttingInsertsRepository> { CuttingInsertsRepositoryRemote() }
    bindSingleton<LinuxCncToolsRepository> { LinuxCncToolsRepositoryRemote() }
    bindSingleton<LatheToolsRepository> { LatheToolsRepositoryRemote() }
    bindSingleton<VarFileRepository> { VarFileRepositoryRemote() }
}