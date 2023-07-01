package startup

import androidx.compose.ui.unit.DpSize
import app.DarkMode
import com.mindovercnc.linuxcnc.IniFilePath

data class StartupArgs(
  val iniFilePath: IniFilePath,
  val topBarEnabled: TopBarEnabled,
  val darkMode: DarkMode,
  val legacyCommunication: Boolean,
  val screenSize: DpSize,
  val density: Double
)
