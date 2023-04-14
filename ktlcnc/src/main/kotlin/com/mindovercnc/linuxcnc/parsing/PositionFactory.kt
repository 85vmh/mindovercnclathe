package com.mindovercnc.linuxcnc.parsing

import com.mindovercnc.linuxcnc.nml.BuffDescriptor
import com.mindovercnc.linuxcnc.nml.Key
import java.nio.ByteBuffer
import ro.dragossusi.proto.linuxcnc.status.Position
import ro.dragossusi.proto.linuxcnc.status.position

class PositionFactory(private val descriptor: BuffDescriptor) {

  fun parse(statusBuffer: ByteBuffer, positionType: PositionType): Position {
    val startOffset = descriptor.entries[positionType.descriptorKey]!!.startOffset
    return position {
      x = statusBuffer.getDouble(startOffset)
      y = statusBuffer.getDouble(startOffset + 8)
      z = statusBuffer.getDouble(startOffset + 16)
      a = statusBuffer.getDouble(startOffset + 24)
      b = statusBuffer.getDouble(startOffset + 32)
      c = statusBuffer.getDouble(startOffset + 40)
      u = statusBuffer.getDouble(startOffset + 48)
      v = statusBuffer.getDouble(startOffset + 56)
      w = statusBuffer.getDouble(startOffset + 64)
    }
  }

  enum class PositionType(val descriptorKey: Key) {
    G5X_OFFSET(Key.G5xOffsetXStart),
    G92_OFFSET(Key.G92OffsetXStart),
    TOOL_OFFSET(Key.ToolOffsetXStart),
    CURRENT_COMMANDED(Key.CommandedPositionXStart),
    CURRENT_ACTUAL(Key.ActualPositionXStart),
    DTG(Key.DtgPositionXStart),
    PROBED(Key.ProbedPositionXStart),
    EXTERNAL_OFFSETS(Key.ExternalOffsetsPositionXStart),
  }
}
