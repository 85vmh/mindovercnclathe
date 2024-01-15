package com.mindovercnc.data.linuxcnc.local.di

import com.mindovercnc.data.linuxcnc.*
import com.mindovercnc.data.linuxcnc.local.*
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val LinuxcncLegacyDataModule = DI.Module("linuxcnc_legacy_data") {
    bindSingleton<CncCommandRepository> { CncCommandRepositoryLocal() }
    bindSingleton<CncStatusRepository> { CncStatusRepositoryLocal(instance(), instance()) }
    bindSingleton<SystemMessageRepository> { SystemMessageRepositoryLocal(instance(), instance()) }
    bindSingleton<IniFileRepository> { IniFileRepositoryLocal(instance()) }
    bindSingleton<VarFileRepository> { VarFileRepositoryLocal(instance(), instance(), instance()) }
    bindSingleton<LinuxCncToolsRepository> { LinuxCncToolsRepositoryLocal(instance(), instance(), instance()) }
    bindSingleton<FileSystemRepository> {
        val iniRepo: IniFileRepository = instance()
        val file = iniRepo.getIniFile().programDir
        println("Program Dir $file")
        FileSystemRepositoryLocal(instance(), file, instance())
    }
    bindProvider { ToolFilePath(instance<IniFileRepository>().getIniFile().toolTableFile) }

    bindProvider { VarFilePath(instance<IniFileRepository>().getIniFile().parameterFile) }
}
