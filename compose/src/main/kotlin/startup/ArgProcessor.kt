package startup

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import app.DarkMode
import com.mindovercnc.linuxcnc.IniFilePath
import com.mindovercnc.linuxcnc.LinuxCncHome
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import mu.KotlinLogging
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

        val sizeString by parser.option(
            ArgType.String,
            shortName = "ss",
            fullName = "screen-size",
            description = "Screen size (WxH))"
        )

        val density by parser.option(
            ArgType.Double,
            shortName = "d",
            fullName = "density",
            description = "Density"
        ).default(1.0)

        parser.parse(args)

        val iniFile = createIniFile(iniPath.toPath())
        if (!fileSystem.exists(iniFile)) {
            throw IllegalArgumentException("$iniPath does not exist")
        }

        return StartupArgs(
            iniFilePath = IniFilePath(iniFile),
            topBarEnabled = TopBarEnabled(topBarEnabled),
            darkMode = darkMode,
            legacyCommunication = legacyCommunication,
            screenSize = sizeFromArg(sizeString),
            density = density
        )
            .also { startupArgs -> LOG.info("Received startup args $startupArgs") }
    }

    private fun sizeFromArg(sizeString: String?): DpSize {
        if (sizeString == null) return defaultScreenSize
        val parts = sizeString.split("x")
        if (parts.size != 2) {
            LOG.warn("Invalid screen size $sizeString, using default $defaultScreenSize")
            return defaultScreenSize
        }
        val width = parts[0].toIntOrNull() ?: run {
            LOG.warn("Invalid screen width ${parts[0]}, using default $defaultScreenSize")
            return defaultScreenSize
        }
        val height = parts[1].toIntOrNull() ?: run {
            LOG.warn("Invalid screen height ${parts[1]}, using default $defaultScreenSize")
            return defaultScreenSize
        }
        return DpSize(width.dp, height.dp)
    }

    private fun createIniFile(file: Path): Path {
        if (fileSystem.exists(file)) return file
        if (file.isAbsolute) throw IllegalArgumentException("$file does not exist")

        // try finding it in linuxcnc folder
        val file = LinuxCncHome.div(file)
        if (!fileSystem.exists(file)) throw IllegalArgumentException("$file does not exist")
        return file
    }

    companion object {
        private val LOG = KotlinLogging.logger("ArgProcessor")
        private val defaultScreenSize = DpSize(1024.dp, 768.dp)
    }
}

@JvmInline
value class TopBarEnabled(val enabled: Boolean)
