package usecase

import com.mindovercnc.data.linuxcnc.CncCommandRepository
import com.mindovercnc.data.linuxcnc.CncStatusRepository
import com.mindovercnc.linuxcnc.tools.VarFileRepository
import kotlinx.coroutines.flow.*
import ro.dragossusi.proto.linuxcnc.status.TaskMode
import usecase.model.OffsetEntry

class OffsetsUseCase(
    private val statusRepository: CncStatusRepository,
    private val commandRepository: CncCommandRepository,
    private val varFileRepository: VarFileRepository
) {

    fun getOffsets(): Flow<List<OffsetEntry>> {
        return varFileRepository.getParametersState()
            .map {
                val offsets = mutableListOf<OffsetEntry>()
                it.coordinateSystems.forEachIndexed { index, position ->
                    offsets.add(OffsetEntry(getStringRepresentation(index + 1), position.x, position.z))
                }
                offsets
            }
    }

    val currentWcs = statusRepository.cncStatusFlow
        .map { it.task_status!!.g5xIndex }
        .map { getStringRepresentation(it) }

    val currentOffset = combine(
        getOffsets(),
        currentWcs
    ) { offsets, current ->
        offsets.find { it.coordinateSystem == current }
    }.distinctUntilChanged()

    suspend fun touchOffX(value: Double) {
        executeMdiCommand("G10 L20 P0 X$value")
    }

    suspend fun touchOffZ(value: Double) {
        executeMdiCommand("G10 L20 P0 Z$value")
    }

    suspend fun setActiveOffset(cmd: String) {
        executeMdiCommand(cmd)
    }

    private suspend fun executeMdiCommand(cmd: String) {
        val initialTaskMode = statusRepository.cncStatusFlow.map { it.task_status!!.taskMode }.first()
        commandRepository.setTaskMode(TaskMode.TaskModeMDI)
        commandRepository.executeMdiCommand(cmd)
        commandRepository.setTaskMode(initialTaskMode)
        commandRepository.setTeleopEnable(true)
    }

    private fun getStringRepresentation(index: Int): String {
        return when (index) {
            0 -> "Current"
            1 -> "G54"
            2 -> "G55"
            3 -> "G56"
            4 -> "G57"
            5 -> "G58"
            6 -> "G59"
            7 -> "G59.1"
            8 -> "G59.2"
            9 -> "G59.3"
            else -> "Gxx"
        }
    }
}