package startup

import com.mindovercnc.linuxcnc.IniFilePath
import com.mindovercnc.linuxcnc.LinuxCncHome
import java.io.File

object LinuxCncStarter {

    private val linuxCncCommand = File(LinuxCncHome, "scripts/linuxcnc")

    operator fun invoke(
        iniFilePath: IniFilePath
    ): Process {
        val process = ProcessBuilder(
            linuxCncCommand.absolutePath,
            iniFilePath.file.absolutePath
        ).start()
        Runtime.getRuntime().addShutdownHook(
            Thread {
                process.destroy()
            }
        )
        return process
    }

}