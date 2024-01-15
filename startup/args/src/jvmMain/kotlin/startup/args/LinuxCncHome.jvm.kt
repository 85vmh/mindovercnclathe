package startup.args

import okio.Path
import okio.Path.Companion.toPath

actual val LinuxCncHome: Path = checkNotNull(System.getenv("LINUXCNC_HOME")) {
    "LINUXCNC_HOME env not set"
}.toPath()