package startup

import app.DarkMode
import com.mindovercnc.linuxcnc.IniFilePath

data class StartupArgs(
    val iniFilePath: IniFilePath,
    val topBarEnabled: TopBarEnabled,
    val darkMode: DarkMode
)