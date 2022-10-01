package com.mindovercnc.linuxcnc

import com.mindovercnc.repository.GCodeRepository
import com.mindovercnc.model.GcodeCommand
import java.io.File

class GCodeRepositoryImpl(
    private val iniFilePath: IniFilePath,
    private val toolFilePath: ToolFilePath,
    private val varFilePath: VarFilePath
) : GCodeRepository {

    private val rs274Path = File(LinuxCncHome, "bin/rs274")

    override fun parseFile(file: File): List<GcodeCommand> {
        val pb = ProcessBuilder(
            rs274Path.path,
            "-g",
            "-v",
            varFilePath.file.path,
            "-i",
            iniFilePath.file.path,
            "-t",
            toolFilePath.file.path,
            file.absolutePath
        )

        val process = pb.start()

        println("Reading gcode from file $file")
        println("START GCODE")
        val commands = process.inputReader().useLines {
            it.map { line ->
                //println(line.colored(PrintColor.BLUE))
                parse(line)
            }.toList()
        }
        println("END GCODE")
        return commands
    }

    private fun parse(line: String): GcodeCommand {
        val id = line.substringBefore("N..... ").trim().toInt()
        //println("\t\tId:\t\t\t\t$id".colored(PrintColor.YELLOW))

        val command = line.substringAfter("N..... ")
        val splitPos = command.indexOf('(')

        val commandName = command.substring(startIndex = 0, endIndex = splitPos)
        //println("\t\tCommandName:\t$commandName".colored(PrintColor.YELLOW))

        val arguments = command.substring(splitPos + 1, command.lastIndex)
//        if (arguments.isNotEmpty()) {
//            println("\t\tArguments:\t\t${arguments}".colored(PrintColor.YELLOW))
//        }

        return GcodeCommand(id, commandName, arguments, line)
    }
}