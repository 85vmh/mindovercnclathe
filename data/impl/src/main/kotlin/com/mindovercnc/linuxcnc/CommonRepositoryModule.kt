package com.mindovercnc.linuxcnc

import com.mindovercnc.repository.*
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import java.util.prefs.Preferences

val CommonDataModule = DI.Module("common_data") {
    bindSingleton<SystemMessageRepository> { SystemMessageRepositoryImpl(instance(), instance()) }
    bindSingleton<MessagesRepository> { MessagesRepositoryImpl(instance(), instance()) }

    bindSingleton<TaskStatusRepository> { TaskStatusRepositoryImpl(instance()) }
    bindSingleton<MotionStatusRepository> { MotionStatusRepositoryImpl(instance()) }
    bindSingleton<IoStatusRepository> { IoStatusRepositoryImpl(instance()) }

    bindSingleton<IniFileRepository> { IniFileRepositoryImpl(instance()) }
    bindSingleton<VarFileRepository> { VarFileRepositoryImpl(instance(), instance(), instance()) }

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
    bindSingleton { Preferences.userRoot() }
    bindSingleton<SettingsRepository> { SettingsRepositoryImpl(instance()) }
    bindSingleton<ActiveLimitsRepository> { ActiveLimitsRepositoryImpl() }

    bindProvider { ToolFilePath(instance<IniFileRepository>().getIniFile().toolTableFile) }

    bindProvider { VarFilePath(instance<IniFileRepository>().getIniFile().parameterFile) }

    bindSingleton<GCodeRepository> {
        GCodeRepositoryImpl(
            iniFilePath = instance(),
            toolFilePath = instance(),
            varFilePath = instance()
        )
    }
}