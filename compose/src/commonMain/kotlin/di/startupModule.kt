package di

import com.mindovercnc.repository.IniFilePath
import okio.Path
import org.kodein.di.DI
import org.kodein.di.bindSingleton

fun startupModule(iniFilePath: Path) =
    DI.Module("startup") { bindSingleton<IniFilePath> { IniFilePath(iniFilePath) } }

