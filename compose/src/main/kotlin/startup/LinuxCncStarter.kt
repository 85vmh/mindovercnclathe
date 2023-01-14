package startup

import com.mindovercnc.linuxcnc.IniFilePath
import com.mindovercnc.linuxcnc.LinuxCncHome

object LinuxCncStarter {

  private val linuxCncCommand = LinuxCncHome.div("scripts/linuxcnc")

  operator fun invoke(iniFilePath: IniFilePath): Process {
    val process = ProcessBuilder(linuxCncCommand.toString(), iniFilePath.file.toString()).start()
    Runtime.getRuntime().addShutdownHook(Thread { process.destroy() })
    return process
  }
}
