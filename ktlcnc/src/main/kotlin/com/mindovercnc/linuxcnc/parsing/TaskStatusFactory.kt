package com.mindovercnc.linuxcnc.parsing

import com.mindovercnc.linuxcnc.model.*
import com.mindovercnc.linuxcnc.nml.BuffDescriptor
import com.mindovercnc.linuxcnc.nml.Key
import java.nio.ByteBuffer
import ro.dragossusi.proto.linuxcnc.status.*

class TaskStatusFactory(
  private val descriptor: BuffDescriptor,
  private val positionFactory: PositionFactory,
) : ParsingFactory<TaskStatus>(descriptor) {

  override fun parse(byteBuffer: ByteBuffer) = taskStatus {
    taskMode = TaskMode.forNumber(byteBuffer.getIntForKey(Key.TaskMode)!!)!!
    taskState = TaskState.forNumber(byteBuffer.getIntForKey(Key.TaskState)!!)!!
    execState = TaskExecState.forNumber(byteBuffer.getIntForKey(Key.ExecState)!!)!!
    interpreterState = InterpreterState.forNumber(byteBuffer.getIntForKey(Key.InterpreterState)!!)!!
    subroutineCallLevel = byteBuffer.getIntForKey(Key.SubroutineCallLevel)!!
    motionLine = byteBuffer.getIntForKey(Key.MotionLine)!!
    readLine = byteBuffer.getIntForKey(Key.ReadLine)!!
    isOptionalStopEnabled = byteBuffer.getBooleanForKey(Key.IsOptionalStop)!!
    isBlockDeleteEnabled = byteBuffer.getBooleanForKey(Key.IsBlockDelete)!!
    isDigitalInputTimeout = byteBuffer.getBooleanForKey(Key.IsDigitalInTimeout)!!
    loadedFile = byteBuffer.getStringForKey(Key.LoadedFilePath)!!
    command = byteBuffer.getStringForKey(Key.Command)!!
    g5XOffset = positionFactory.parse(byteBuffer, PositionFactory.PositionType.G5X_OFFSET)
    g5XIndex = byteBuffer.getIntForKey(Key.G5xActiveIndex)!!
    g92Offset = positionFactory.parse(byteBuffer, PositionFactory.PositionType.G92_OFFSET)
    rotationXY = byteBuffer.getDoubleForKey(Key.RotationXY)!!
    toolOffset = positionFactory.parse(byteBuffer, PositionFactory.PositionType.TOOL_OFFSET)
    activeCodes = activeCodes {
      gCodes.addAll(
        parseActiveCodes(
          byteBuffer,
          descriptor.entries[Key.ActiveGCodes]!!.startOffset,
          ActiveCodeType.G_CODE
        )
      )
      mCodes.addAll(
        parseActiveCodes(
          byteBuffer,
          descriptor.entries[Key.ActiveMCodes]!!.startOffset,
          ActiveCodeType.M_CODE
        )
      )
    }
    activeSettings.addAll(parseActiveSettings(byteBuffer))
    programUnits = LengthUnit.forNumber(byteBuffer.getIntForKey(Key.ProgramUnits)!!)!!
    intepreterErrorCode = byteBuffer.getIntForKey(Key.InterpreterErrorCode)!!
    isTaskPaused = byteBuffer.getBooleanForKey(Key.TaskPaused)!!
    delayLeft = byteBuffer.getDoubleForKey(Key.DelayLeft)!!
    mdiInputQueue = byteBuffer.getIntForKey(Key.QueuedMdiCommands)!!
  }

  private fun parseActiveCodes(
    statusBuffer: ByteBuffer,
    offset: Int,
    activeCodeType: ActiveCodeType
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
    G_CODE(16, 10f),
    M_CODE(10, 1f)
  }
}
