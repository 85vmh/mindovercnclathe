package com.mindovercnc.linuxcnc.parsing

import com.mindovercnc.linuxcnc.nml.BuffDescriptor
import com.mindovercnc.linuxcnc.nml.Key
import ro.dragossusi.proto.linuxcnc.status.*
import java.nio.ByteBuffer

class TaskStatusFactory(
    private val descriptor: BuffDescriptor,
    private val positionFactory: PositionFactory,
) : ParsingFactory<TaskStatus>(descriptor) {

    override fun parse(byteBuffer: ByteBuffer) = TaskStatus(
        taskMode = TaskMode.fromValue(byteBuffer.getIntForKey(Key.TaskMode)!! - 1)!!,
        taskState = TaskState.fromValue(byteBuffer.getIntForKey(Key.TaskState)!! - 1)!!, //enums start from 0, in linuxcnc it starts from 1
        execState = TaskExecState.fromValue(byteBuffer.getIntForKey(Key.ExecState)!! - 1)!!,
        interpreterState = InterpreterState.fromValue(byteBuffer.getIntForKey(Key.InterpreterState)!! - 1)!!,
        subroutineCallLevel = byteBuffer.getIntForKey(Key.SubroutineCallLevel)!!,
        motionLine = byteBuffer.getIntForKey(Key.MotionLine)!!,
        readLine = byteBuffer.getIntForKey(Key.ReadLine)!!,
        isOptionalStopEnabled = byteBuffer.getBooleanForKey(Key.IsOptionalStop)!!,
        isBlockDeleteEnabled = byteBuffer.getBooleanForKey(Key.IsBlockDelete)!!,
        isDigitalInputTimeout = byteBuffer.getBooleanForKey(Key.IsDigitalInTimeout)!!,
        loadedFile = byteBuffer.getStringForKey(Key.LoadedFilePath)!!,
        command = byteBuffer.getStringForKey(Key.Command)!!,
        g5xOffset = positionFactory.parse(byteBuffer, PositionFactory.PositionType.G5X_OFFSET),
        g5xIndex = byteBuffer.getIntForKey(Key.G5xActiveIndex)!!,
        g92Offset = positionFactory.parse(byteBuffer, PositionFactory.PositionType.G92_OFFSET),
        rotationXY = byteBuffer.getDoubleForKey(Key.RotationXY)!!,
        toolOffset = positionFactory.parse(byteBuffer, PositionFactory.PositionType.TOOL_OFFSET),
        activeCodes = ActiveCodes(
            g_codes = parseActiveCodes(
                byteBuffer,
                descriptor.entries[Key.ActiveGCodes]!!.startOffset,
                ActiveCodeType.G_CODE
            ),
            m_codes = parseActiveCodes(
                byteBuffer,
                descriptor.entries[Key.ActiveMCodes]!!.startOffset,
                ActiveCodeType.M_CODE
            )
        ),
        activeSettings = parseActiveSettings(byteBuffer),
        programUnits = LengthUnit.fromValue(byteBuffer.getIntForKey(Key.ProgramUnits)!!)!!,
        intepreterErrorCode = byteBuffer.getIntForKey(Key.InterpreterErrorCode)!!,
        isTaskPaused = byteBuffer.getBooleanForKey(Key.TaskPaused)!!,
        delayLeft = byteBuffer.getDoubleForKey(Key.DelayLeft)!!,
        mdiInputQueue = byteBuffer.getIntForKey(Key.QueuedMdiCommands)!!,
    )

    private fun parseActiveCodes(
        statusBuffer: ByteBuffer, offset: Int, activeCodeType: ActiveCodeType
    ): List<Float> {
        val result = mutableListOf<Float>()
        for (i in 0 until activeCodeType.maxCodes) {
            val code = statusBuffer.getInt(offset + 4 * i)
            if (code > 0) {
                result.add(code / activeCodeType.divideBy)
            }
        }
        return result
    }

    private fun parseActiveSettings(statusBuffer: ByteBuffer): List<Double> {
        val result = mutableListOf<Double>()
        for (i in 0 until 5) {
            result.add(statusBuffer.getDoubleForKey(Key.ActiveSettings, 8 * i) ?: 0.0)
        }
        return result
    }

    internal enum class ActiveCodeType(val maxCodes: Int, val divideBy: Float) {
        G_CODE(16, 10f), M_CODE(10, 1f)
    }
}
