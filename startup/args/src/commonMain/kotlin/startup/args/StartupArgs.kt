package startup.args

import androidx.compose.ui.unit.DpSize
import okio.Path

data class StartupArgs(
    val iniFilePath: Path,
    val topBarEnabled: TopBarEnabled,
    val darkMode: DarkMode,
    val legacyCommunication: Boolean,
    val screenSize: DpSize,
    val density: Double
)
