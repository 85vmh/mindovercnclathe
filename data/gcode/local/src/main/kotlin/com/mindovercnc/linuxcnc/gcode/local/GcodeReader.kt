package com.mindovercnc.linuxcnc.gcode.local

import com.mindovercnc.data.linuxcnc.IniFilePath
import com.mindovercnc.data.linuxcnc.ToolFilePath
import com.mindovercnc.data.linuxcnc.VarFilePath
import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.linuxcnc.gcode.model.GCodeCommand
import com.mindovercnc.log.PrintColor
import com.mindovercnc.log.colored
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import okio.Path

class GcodeReader(
    private val iniFilePath: IniFilePath,
    private val toolFilePath: ToolFilePath,
    private val varFilePath: VarFilePath,
    private val parser: GcodeParser,
    private val ioDispatcher: IoDispatcher
) {

    // This is the path to the stand alone rs274 interpreter binary
    private val rs274Path = LinuxCncHome.div("bin/rs274")

    suspend fun readFile(path: Path): List<GCodeCommand> =
        withContext(ioDispatcher.dispatcher) {
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
                    path.toString()
                )

            val commandString = pb.command().joinToString(separator = " ")
            LOG.info { "Executing RS274 command:\n${commandString.colored(PrintColor.BLUE)}" }
            val process = pb.start()

            LOG.debug { "Reading gcode from file $path" }
            LOG.debug { "START GCODE" }
            val commands = process.inputStream.reader().useLines { it.map(parser::parse).toList() }
            LOG.debug { "END GCODE" }
            commands
        }

    private companion object {
        val LOG = KotlinLogging.logger("GcodeReader")
    }
}
