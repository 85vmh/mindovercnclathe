import androidx.compose.ui.unit.DpSize
import okio.Path
import startup.args.DarkMode

class AppConfig(
    val iniFile: Path,
    val communication: Communication,
    val screenSize: DpSize,
    val topBarEnabled: Boolean,
    val darkMode: DarkMode,
    val density: Double
)
