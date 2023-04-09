package di

import com.mindovercnc.linuxcnc.*
import com.mindovercnc.linuxcnc.legacy.CncCommandRepositoryLegacy
import com.mindovercnc.linuxcnc.legacy.CncStatusRepositoryLegacy
import com.mindovercnc.linuxcnc.legacy.HalRepositoryLegacy
import com.mindovercnc.linuxcnc.nml.BuffDescriptor
import com.mindovercnc.linuxcnc.nml.BuffDescriptorV29
import com.mindovercnc.linuxcnc.parsing.*
import com.mindovercnc.repository.*
import java.util.prefs.Preferences
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import startup.StartupArgs

fun repositoryModule(legacyCommunication: Boolean) =
  DI.Module("repository") {
    import(CommonRepositoryModule)
    import(if (legacyCommunication) LegacyRepositoryModule else GrpcRepositoryModule)
  }

private val LegacyRepositoryModule =
  DI.Module("legacy_repository") {
    bindSingleton<CncCommandRepository> { CncCommandRepositoryLegacy() }
    bindSingleton<HalRepository> { HalRepositoryLegacy() }
    bindSingleton<CncStatusRepository> { CncStatusRepositoryLegacy(instance(), instance()) }
  }

private val GrpcRepositoryModule =
  DI.Module("legacy_repository") {
    bindSingleton<CncCommandRepository> { CncCommandRepositoryImpl(instance()) }
    bindSingleton<HalRepository> { HalRepositoryImpl(instance()) }
    bindSingleton<CncStatusRepository> {
      CncStatusRepositoryImpl(instance(), instance(), instance())
    }
  }

private val CommonRepositoryModule =
  DI.Module("common_repository") {
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

fun startupModule(startupArgs: StartupArgs) =
  DI.Module("startup") { bindSingleton { startupArgs.iniFilePath } }

val BuffDescriptorModule =
  DI.Module("buffDescriptor") { bindSingleton<BuffDescriptor> { BuffDescriptorV29() } }

val ParseFactoryModule =
  DI.Module("parseFactory") {
    bindSingleton { PositionFactory(instance()) }
    bindSingleton { TaskStatusFactory(instance(), instance()) }
    bindSingleton { TrajectoryStatusFactory(instance(), instance()) }
    bindSingleton { JointStatusFactory(instance()) }
    bindSingleton { SpindleStatusFactory(instance()) }
    bindSingleton {
      MotionStatusFactory(instance(), instance(), instance(), instance(), instance())
    }
    bindSingleton { IoStatusFactory(instance()) }
    bindSingleton { CncStatusFactory(instance(), instance(), instance(), instance()) }
  }
