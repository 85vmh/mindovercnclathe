package startup

import app.DarkMode
import com.mindovercnc.linuxcnc.IniFilePath
import com.mindovercnc.linuxcnc.LinuxCncHome
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath

class ArgProcessor(private val fileSystem: FileSystem) {
  fun process(args: Array<String>): StartupArgs {
    val parser = ArgParser("MindOverCNCLathe")

    val darkMode by
      parser
        .option(
          ArgType.Choice<DarkMode>(),
          shortName = "dm",
          fullName = "dark_mode",
          description = "Choose dark mode"
        )
        .default(DarkMode.SYSTEM)

    val legacyCommunication by
      parser
        .option(
          ArgType.Boolean,
          fullName = "legacy-communication",
          description = "Use legacy communication instead of gRPC"
        )
        .default(false)

    val topBarEnabled by
      parser
        .option(
          ArgType.Boolean,
          shortName = "tb",
          fullName = "topbar-enabled",
          description = "Set topbar enabled"
        )
        .default(false)

    val iniPath by parser.argument(ArgType.String, description = "INI file path to load")

    parser.parse(args)

    val iniFile = createIniFile(iniPath.toPath())
    if (!fileSystem.exists(iniFile)) {
      throw IllegalArgumentException("$iniPath does not exist")
    }

    return StartupArgs(
      iniFilePath = IniFilePath(iniFile),
      topBarEnabled = TopBarEnabled(topBarEnabled),
      darkMode = darkMode,
      legacyCommunication = legacyCommunication
    )
  }

  private fun createIniFile(file: Path): Path {
    if (fileSystem.exists(file)) return file
    if (file.isAbsolute) throw IllegalArgumentException("$file does not exist")

    // try finding it in linuxcnc folder
    val file = LinuxCncHome.div(file)
    if (!fileSystem.exists(file)) throw IllegalArgumentException("$file does not exist")
    return file
  }
}

@JvmInline value class TopBarEnabled(val enabled: Boolean)
