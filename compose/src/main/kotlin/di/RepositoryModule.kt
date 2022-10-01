package di

import com.mindovercnc.linuxcnc.*
import com.mindovercnc.linuxcnc.nml.BuffDescriptor
import com.mindovercnc.linuxcnc.nml.BuffDescriptorV29
import com.mindovercnc.linuxcnc.parsing.*
import com.mindovercnc.repository.*
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import startup.StartupArgs
import java.util.prefs.Preferences

val RepositoryModule = DI.Module("repository") {
    bindSingleton<CncStatusRepository> { CncStatusRepositoryImpl(instance("app_scope"), instance()) }
    bindSingleton<MessagesRepository> { MessagesRepositoryImpl(instance("app_scope")) }
    bindSingleton<CncCommandRepository> { CncCommandRepositoryImpl() }
    bindSingleton<HalRepository> { HalRepositoryImpl() }
    bindSingleton<IniFileRepository> { IniFileRepositoryImpl(instance()) }
    bindSingleton<VarFileRepository> {
        VarFileRepositoryImpl(instance("app_scope"), instance())
    }

    bindSingleton<ToolHolderRepository> { ToolHolderRepositoryImpl() }
    bindSingleton<LatheToolsRepository> { LatheToolsRepositoryImpl() }
    bindSingleton<CuttingInsertsRepository> { CuttingInsertsRepositoryImpl() }
    bindSingleton<WorkpieceMaterialRepository> { WorkpieceMaterialRepositoryImpl() }

    bindSingleton<FileSystemRepository> {
        val iniRepo: IniFileRepository = instance()
        val file = iniRepo.getIniFile().programDir
        FileSystemRepositoryImpl(instance("app_scope"), file)
    }
    bindSingleton { Preferences.userRoot() }
    bindSingleton<SettingsRepository> { SettingsRepositoryImpl(instance()) }
    bindSingleton<ActiveLimitsRepository> { ActiveLimitsRepository() }
    
    bindProvider {
        ToolFilePath(instance<IniFileRepository>().getIniFile().toolTableFile)
    }

    bindProvider {
        VarFilePath(instance<IniFileRepository>().getIniFile().parameterFile)
    }

    bindSingleton<GCodeRepository> {
        GCodeRepositoryImpl(
            iniFilePath = instance(),
            toolFilePath = instance(),
            varFilePath = instance()
        )
    }
}

fun startupModule(startupArgs: StartupArgs) = DI.Module("startup") {
    bindSingleton { startupArgs.iniFilePath }
}

fun appScopeModule(scope: CoroutineScope) = DI.Module("app_scope") {
    bindProvider("app_scope") { scope }
}

val BuffDescriptorModule = DI.Module("buffDescriptor") {
    bindSingleton<BuffDescriptor> {
        BuffDescriptorV29()
    }
}

val ParseFactoryModule = DI.Module("parseFactory") {
    bindSingleton { PositionFactory(instance()) }
    bindSingleton { TaskStatusFactory(instance(), instance()) }
    bindSingleton { TrajectoryStatusFactory(instance(), instance()) }
    bindSingleton { JointStatusFactory(instance()) }
    bindSingleton { SpindleStatusFactory(instance()) }
    bindSingleton { MotionStatusFactory(instance(), instance(), instance(), instance(), instance()) }
    bindSingleton { IoStatusFactory(instance()) }
    bindSingleton { CncStatusFactory(instance(), instance(), instance(), instance()) }
}