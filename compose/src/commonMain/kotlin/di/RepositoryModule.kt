package di

import com.mindovercnc.linuxcnc.CncCommandRepositoryImpl
import com.mindovercnc.linuxcnc.CncStatusRepositoryImpl
import com.mindovercnc.linuxcnc.HalRepositoryImpl
import com.mindovercnc.repository.CncCommandRepository
import com.mindovercnc.repository.CncStatusRepository
import com.mindovercnc.repository.HalRepository
import com.mindovercnc.repository.IniFilePath
import okio.Path
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

private val GrpcRepositoryModule =
    DI.Module("grpc_repository") {
        bindSingleton<CncCommandRepository> { CncCommandRepositoryImpl(instance()) }
        bindSingleton<HalRepository> { HalRepositoryImpl(instance()) }
        bindSingleton<CncStatusRepository> { CncStatusRepositoryImpl(instance(), instance()) }
    }

fun startupModule(iniFilePath: Path) =
    DI.Module("startup") { bindSingleton<IniFilePath> { IniFilePath(iniFilePath) } }

