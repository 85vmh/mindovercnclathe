package startup.args

import okio.Path
import okio.Path.Companion.toPath

actual val LinuxCncHome: Path =  System.getenv("LINUXCNC_HOME").toPath()