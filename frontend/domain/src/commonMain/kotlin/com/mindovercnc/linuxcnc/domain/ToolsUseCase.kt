package com.mindovercnc.linuxcnc.domain

import com.mindovercnc.data.lathehal.HalRepository
import com.mindovercnc.data.linuxcnc.CncCommandRepository
import com.mindovercnc.data.linuxcnc.CncStatusRepository
import com.mindovercnc.data.linuxcnc.VarFileRepository
import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.dispatchers.createScope
import com.mindovercnc.linuxcnc.settings.SettingsRepository
import com.mindovercnc.linuxcnc.settings.model.IntegerKey
import com.mindovercnc.linuxcnc.tools.CuttingInsertsRepository
import com.mindovercnc.linuxcnc.tools.LatheToolsRepository
import com.mindovercnc.linuxcnc.tools.ToolHolderRepository
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert
import com.mindovercnc.linuxcnc.tools.model.LatheTool
import com.mindovercnc.linuxcnc.tools.model.ToolHolder
import com.mindovercnc.repository.EmcMessagesRepository
import com.mindovercnc.repository.IoStatusRepository
import com.mindovercnc.repository.MotionStatusRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import linuxcnc.currentToolNo
import linuxcnc.isHomed
import ro.dragossusi.proto.linuxcnc.status.TaskMode

class ToolsUseCase(
    ioDispatcher: IoDispatcher,
    private val statusRepository: CncStatusRepository,
    private val motionStatusRepository: MotionStatusRepository,
    private val ioStatusRepository: IoStatusRepository,
    private val commandRepository: CncCommandRepository,
    private val messagesRepository: EmcMessagesRepository,
    private val halRepository: HalRepository,
    private val settingsRepository: SettingsRepository,
    private val toolHolderRepository: ToolHolderRepository,
    private val latheToolsRepository: LatheToolsRepository,
    private val cuttingInsertsRepository: CuttingInsertsRepository,
    private val varFileRepository: VarFileRepository
) {

    private val scope = ioDispatcher.createScope()

    init {
        // TODO: this should be moved somewhere else, and remove the scope from the useCase
        motionStatusRepository.motionStatusFlow
            .map { it.isHomed(2) }
            .filter { it }
            .distinctUntilChanged()
            .onEach {
                val lastTool = settingsRepository.get(IntegerKey.LastToolUsed)
                if (lastTool != 0) {
                    loadTool(lastTool)
                }
            }
            .launchIn(scope)
    }

    suspend fun getLatheTools(): List<LatheTool> = latheToolsRepository.getLatheTools()

    suspend fun createLatheTool(latheTool: LatheTool) =
        latheToolsRepository.createLatheTool(latheTool)

    suspend fun updateLatheTool(latheTool: LatheTool) =
        latheToolsRepository.updateLatheTool(latheTool)

    suspend fun createCuttingInsert(cuttingInsert: CuttingInsert) =
        cuttingInsertsRepository.insert(cuttingInsert)

    suspend fun updateCuttingInsert(cuttingInsert: CuttingInsert) =
        cuttingInsertsRepository.update(cuttingInsert)

    fun getCuttingInserts(): Flow<List<CuttingInsert>> = flowOf(cuttingInsertsRepository.findAll())

    suspend fun deleteToolHolder(toolHolder: ToolHolder) =
        toolHolderRepository.deleteToolHolder(toolHolder)

    suspend fun deleteLatheTool(tool: LatheTool) = latheToolsRepository.deleteLatheTool(tool)

    suspend fun deleteCuttingInsert(insert: CuttingInsert) = cuttingInsertsRepository.delete(insert)

    fun getTools(): Flow<List<LatheTool>> {
        // return toolsRepository.getTools()
        return flowOf(emptyList())
    }

    suspend fun toolTouchOffX(value: Double) {
        toolTouchOff("X$value")
    }

    suspend fun toolTouchOffZ(value: Double) {
        toolTouchOff("Z$value")
    }

    suspend fun loadTool(toolNo: Int) {
        val initialTaskMode =
            statusRepository.cncStatusFlow.map { it.task_status!!.taskMode }.first()
        commandRepository.setTaskMode(TaskMode.TaskModeMDI)
        commandRepository.executeMdiCommand("M61 Q$toolNo G43")
        delay(200)
        commandRepository.setTaskMode(initialTaskMode)
    }

    fun deleteTool(toolNo: Int) {
        // toolsRepository.removeTool(toolNo)
    }

    fun getCurrentToolNo(): Flow<Int> {
        return ioStatusRepository.ioStatusFlow
            .map { it.currentToolNo!! }
            .distinctUntilChanged()
            .onEach { settingsRepository.put(IntegerKey.LastToolUsed, it) }
    }

    //    fun getCurrentTool(): Flow<LatheTool?> {
    //        return combine(
    //            toolsRepository.getTools().distinctUntilChanged(),
    //            getCurrentToolNo()
    //        ) { toolsList, loadedToolNo ->
    //            toolsList.find { it.toolNo == loadedToolNo }
    //        }
    //    }

    private suspend fun toolTouchOff(axisWithValue: String) {
        val initialTaskMode =
            statusRepository.cncStatusFlow.map { it.task_status!!.taskMode }.first()
        val currentTool = ioStatusRepository.ioStatusFlow.map { it.currentToolNo }.first()
        commandRepository.setTaskMode(TaskMode.TaskModeMDI)
        commandRepository.executeMdiCommand("G10 L10 P$currentTool $axisWithValue")
        // TODO: make this based on status channel
        delay(200)
        commandRepository.executeMdiCommand("G43")
        delay(200)
        commandRepository.setTaskMode(initialTaskMode)
        commandRepository.setTeleopEnable(true)
    }
}
