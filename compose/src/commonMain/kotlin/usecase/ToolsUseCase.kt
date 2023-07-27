package usecase

import com.mindovercnc.data.linuxcnc.CncCommandRepository
import com.mindovercnc.data.linuxcnc.CncStatusRepository
import com.mindovercnc.data.linuxcnc.HalRepository
import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.dispatchers.createScope
import com.mindovercnc.model.*
import com.mindovercnc.repository.*
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
    private val messagesRepository: MessagesRepository,
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

    fun getToolHolders(): Flow<List<ToolHolder>> = flowOf(toolHolderRepository.getToolHolders())

    fun createToolHolder(toolHolder: ToolHolder) = toolHolderRepository.createToolHolder(toolHolder)

    fun updateToolHolder(toolHolder: ToolHolder) = toolHolderRepository.updateToolHolder(toolHolder)

    fun getLatheTools(): Flow<List<LatheTool>> = flowOf(latheToolsRepository.getLatheTools())

    fun getUnmountedLatheTools(holderType: ToolHolderType): Flow<List<LatheTool>> {
        val allTools = latheToolsRepository.getUnmountedLatheTools()
        val filteredTools = when (holderType) {
            ToolHolderType.Generic -> allTools.filter {
                it is LatheTool.Turning || it is LatheTool.Boring ||
                        it is LatheTool.Parting || it is LatheTool.Grooving ||
                        it is LatheTool.OdThreading || it is LatheTool.IdThreading ||
                        it is LatheTool.Slotting
            }

            ToolHolderType.Centered -> allTools.filter {
                it is LatheTool.Drilling || it is LatheTool.Reaming
            }

            ToolHolderType.Parting -> allTools.filter {
                it is LatheTool.Parting ||
                        (it is LatheTool.Grooving && it.tipOrientation == TipOrientation.Position6)
            }

            ToolHolderType.Boring -> allTools.filter {
                it is LatheTool.Boring ||
                        (it is LatheTool.Grooving && it.tipOrientation == TipOrientation.Position8)
            }
        }
        return flowOf(filteredTools)
    }

    fun createLatheTool(latheTool: LatheTool) = latheToolsRepository.createLatheTool(latheTool)

    fun updateLatheTool(latheTool: LatheTool) = latheToolsRepository.updateLatheTool(latheTool)

    fun createCuttingInsert(cuttingInsert: CuttingInsert) =
        cuttingInsertsRepository.insert(cuttingInsert)

    fun updateCuttingInsert(cuttingInsert: CuttingInsert) =
        cuttingInsertsRepository.update(cuttingInsert)

    fun getCuttingInserts(): Flow<List<CuttingInsert>> = flowOf(cuttingInsertsRepository.findAll())

    fun deleteToolHolder(toolHolder: ToolHolder) = toolHolderRepository.deleteToolHolder(toolHolder)

    fun deleteLatheTool(tool: LatheTool) = latheToolsRepository.deleteLatheTool(tool)

    fun deleteCuttingInsert(insert: CuttingInsert) = cuttingInsertsRepository.delete(insert)

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
        val initialTaskMode = statusRepository.cncStatusFlow.map { it.task_status!!.taskMode }.first()
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
        val initialTaskMode = statusRepository.cncStatusFlow.map { it.task_status!!.taskMode }.first()
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
