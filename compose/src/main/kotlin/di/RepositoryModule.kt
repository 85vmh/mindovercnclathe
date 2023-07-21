package di

import com.mindovercnc.linuxcnc.CncCommandRepositoryImpl
import com.mindovercnc.linuxcnc.CncStatusRepositoryImpl
import com.mindovercnc.linuxcnc.CommonDataModule
import com.mindovercnc.linuxcnc.HalRepositoryImpl
import com.mindovercnc.linuxcnc.legacy.LegacyDataModule
import com.mindovercnc.linuxcnc.parsing.*
import com.mindovercnc.repository.CncCommandRepository
import com.mindovercnc.repository.CncStatusRepository
import com.mindovercnc.repository.HalRepository
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import startup.StartupArgs

fun repositoryModule(legacyCommunication: Boolean) =
    DI.Module("repository") {
        import(CommonDataModule)
        import(if (legacyCommunication) LegacyDataModule else GrpcRepositoryModule)
    }

private val GrpcRepositoryModule =
    DI.Module("grpc_repository") {
        bindSingleton<CncCommandRepository> { CncCommandRepositoryImpl(instance()) }
        bindSingleton<HalRepository> { HalRepositoryImpl(instance()) }
        bindSingleton<CncStatusRepository> { CncStatusRepositoryImpl(instance(), instance()) }
    }

fun startupModule(startupArgs: StartupArgs) =
    DI.Module("startup") { bindSingleton { startupArgs.iniFilePath } }

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
