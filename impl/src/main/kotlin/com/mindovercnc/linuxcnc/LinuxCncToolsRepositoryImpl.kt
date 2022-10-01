package com.mindovercnc.linuxcnc

import com.mindovercnc.model.LinuxCncTool
import com.mindovercnc.model.TipOrientation
import com.mindovercnc.repository.LinuxCncToolsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class LinuxCncToolsRepositoryImpl(
    private val scope: CoroutineScope,
    private val toolTableFilePath: ToolFilePath
) : LinuxCncToolsRepository {

    private val toolList = MutableStateFlow(emptyList<LinuxCncTool>())
    private val toolMap = mutableMapOf<Int, String>()

    init {
        //when a tool offset is updated, the file is rewritten, so we reload it
        FileWatcher.watchChanges(scope, toolTableFilePath.file.path, true) {
            println("---$toolTableFilePath changed, reloading")
            readFile()
        }
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
        toolTableFilePath.file.forEachLine { aLine ->
            with(parseToolLine(aLine)) {
                newToolList.add(this)
                toolMap[this.toolNo] = aLine
            }
        }
        toolList.value = emptyList()
        toolList.update {
            it.plus(newToolList)
        }
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
        scope.launch(Dispatchers.IO) {
            toolTableFilePath.file.printWriter().use {
                toolMap.entries.forEach { line ->
                    it.println(line.value)
                }
                it.close()
            }
        }
    }
}