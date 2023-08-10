package com.mindovercnc.linuxcnc.tools.local

import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.dispatchers.createScope
import com.mindovercnc.linuxcnc.gcode.ToolFilePath
import com.mindovercnc.linuxcnc.tools.LinuxCncToolsRepository
import com.mindovercnc.model.LinuxCncTool
import com.mindovercnc.model.TipOrientation
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okio.FileSystem

/** Implementation for [LinuxCncToolsRepository]. */
class LinuxCncToolsRepositoryLocal(
    private val ioDispatcher: IoDispatcher,
    private val toolTableFilePath: ToolFilePath,
    private val fileSystem: FileSystem
) : LinuxCncToolsRepository {

    private val scope = ioDispatcher.createScope()

    private val toolList = MutableStateFlow(emptyList<LinuxCncTool>())
    private val toolMap = mutableMapOf<Int, String>()

    init {
        // when a tool offset is updated, the file is rewritten, so we reload it
        FileWatcher.watchChanges(toolTableFilePath.file.toString(), true)
            .onEach {
                println("---$toolTableFilePath changed, reloading")
                readFile()
            }
            .launchIn(scope)
    }

    override fun getTools(): Flow<List<LinuxCncTool>> {
        return toolList
    }

    override fun addOrUpdateTool(latheTool: LinuxCncTool) {
        toolMap[latheTool.toolNo] = latheTool.toFormattedString()
        updateToolTableFile()
    }

    override fun removeTool(toolNo: Int) {
        toolMap.remove(toolNo)
        updateToolTableFile()
    }

    private fun readFile() {
        val newToolList = mutableListOf<LinuxCncTool>()
        toolMap.clear()
        fileSystem.read(file = toolTableFilePath.file) {
            while (true) {
                val line = readUtf8Line() ?: break

                with(parseToolLine(line)) {
                    newToolList.add(this)
                    toolMap[this.toolNo] = line
                }
            }
        }
        toolList.value = emptyList()
        toolList.update { it.plus(newToolList) }
    }

    private fun parseToolLine(line: String): LinuxCncTool {
        val builder = LinuxCncTool.Builder()
        val elements = line.split(" ")
        for (element in elements) {
            when {
                element.startsWith("T") -> builder.toolNo = element.substring(1).toInt()
                element.startsWith("X") -> builder.xOffset = element.substring(1).toDouble()
                element.startsWith("Z") -> builder.zOffset = element.substring(1).toDouble()
                element.startsWith("D") -> builder.tipRadius = element.substring(1).toDouble()
                element.startsWith("I") -> builder.frontAngle = element.substring(1).toDouble()
                element.startsWith("J") -> builder.backAngle = element.substring(1).toDouble()
                element.startsWith("Q") -> {
                    val orientValue = element.substring(1).toInt()
                    builder.orientation = TipOrientation.getOrientation(orientValue)
                }

                element.startsWith(";") -> break
            }
        }
        if (line.contains(";")) {
            builder.comment = line.substring(line.indexOf(";"))
        }
        return builder.build()
    }

    private fun updateToolTableFile() {
        scope.launch {
            fileSystem.write(toolTableFilePath.file) {
                toolMap.entries.forEach { line -> writeUtf8(line.value) }
            }
        }
    }
}
