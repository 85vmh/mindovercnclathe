package com.mindovercnc.linuxcnc

import com.mindovercnc.repository.*
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val CommonDataModule = DI.Module("common_data") {
    bindSingleton<MessagesRepository> { MessagesRepositoryImpl(instance(), instance(), instance()) }

    bindSingleton<TaskStatusRepository> { TaskStatusRepositoryImpl(instance()) }
    bindSingleton<MotionStatusRepository> { MotionStatusRepositoryImpl(instance()) }
    bindSingleton<IoStatusRepository> { IoStatusRepositoryImpl(instance()) }

    bindSingleton<IniFileRepository> { IniFileRepositoryImpl() }
    bindSingleton<VarFileRepository> { VarFileRepositoryImpl() }

    bindSingleton<ToolHolderRepository> { ToolHolderRepositoryImpl() }
    bindSingleton<LatheToolsRepository> { LatheToolsRepositoryImpl() }
    bindSingleton<CuttingInsertsRepository> { CuttingInsertsRepositoryImpl() }
    bindSingleton<WorkpieceMaterialRepository> { WorkpieceMaterialRepositoryImpl() }

    bindSingleton<FileSystemRepository> {
        val iniRepo: IniFileRepository = instance()
        val file = iniRepo.getIniFile().programDir
        println("Program Dir $file")
        FileSystemRepositoryImpl(instance(), file, instance())
    }
    bindSingleton<SettingsRepository> { SettingsRepositoryImpl() }
    bindSingleton<ActiveLimitsRepository> { ActiveLimitsRepositoryImpl() }

    bindProvider { ToolFilePath(instance<IniFileRepository>().getIniFile().toolTableFile) }

    bindProvider { VarFilePath(instance<IniFileRepository>().getIniFile().parameterFile) }

    bindSingleton<GCodeRepository> {
        GCodeRepositoryImpl()
    }
}