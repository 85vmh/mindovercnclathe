package com.mindovercnc.linuxcnc.gcode.local

import com.mindovercnc.data.linuxcnc.IniFilePath
import com.mindovercnc.data.linuxcnc.ToolFilePath
import com.mindovercnc.data.linuxcnc.VarFilePath
import com.mindovercnc.linuxcnc.gcode.GCodeInterpreterRepository
import com.mindovercnc.linuxcnc.gcode.GCodeCommand
import okio.Path
import okio.Path.Companion.toPath

//val LinuxCncHome = System.getenv("LINUXCNC_HOME").toPath()
val LinuxCncHome: Path = "/home/vasimihalca/Work/linuxcnc-dev2".toPath()

/** Implementation for [GCodeInterpreterRepository]. */
class GCodeInterpreterRepositoryLocal(
    private val iniFilePath: IniFilePath,
    private val toolFilePath: ToolFilePath,
    private val varFilePath: VarFilePath
) : GCodeInterpreterRepository {

    // This is the path to the stand alone rs274 interpreter binary
    private val rs274Path = LinuxCncHome.div("bin/rs274")

    override fun parseFile(file: Path): List<GCodeCommand> {
        val pb =
            ProcessBuilder(
                rs274Path.toString(),
                "-g",
                "-v",
                varFilePath.file.toString(),
                "-i",
                iniFilePath.file.toString(),
                "-t",
                toolFilePath.file.toString(),
                file.toString()
            )

        val process = pb.start()

        println("Reading gcode from file $file")
        println("START GCODE")
        val commands =
            process.inputStream.reader().useLines {
                it
                    .map { line ->
                        // println(line.colored(PrintColor.BLUE))
                        parse(line)
                    }
                    .toList()
            }
        println("END GCODE")
        return commands
    }

    private fun parse(line: String): GCodeCommand {
        val id = line.substringBefore("N..... ").trim().toInt()
        // println("\t\tId:\t\t\t\t$id".colored(PrintColor.YELLOW))

        val command = line.substringAfter("N..... ")
        val splitPos = command.indexOf('(')

        val commandName = command.substring(startIndex = 0, endIndex = splitPos)
        // println("\t\tCommandName:\t$commandName".colored(PrintColor.YELLOW))

        val arguments = command.substring(splitPos + 1, command.lastIndex)
        //        if (arguments.isNotEmpty()) {
        //            println("\t\tArguments:\t\t${arguments}".colored(PrintColor.YELLOW))
        //        }

        return GCodeCommand(id, commandName, arguments, line)
    }
}
