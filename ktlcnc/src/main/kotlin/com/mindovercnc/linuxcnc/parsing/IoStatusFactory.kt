package com.mindovercnc.linuxcnc.parsing

import com.mindovercnc.linuxcnc.nml.BuffDescriptor
import com.mindovercnc.linuxcnc.nml.Key
import java.nio.ByteBuffer
import ro.dragossusi.proto.linuxcnc.status.*

class IoStatusFactory(descriptor: BuffDescriptor) : ParsingFactory<IoStatus>(descriptor) {

  override fun parse(byteBuffer: ByteBuffer) = ioStatus {
    cycleTime = byteBuffer.getDoubleForKey(Key.IoCycleTime)!!
    reason = byteBuffer.getIntForKey(Key.IoReason)!!
    fault = byteBuffer.getBooleanForKey(Key.IoFaultDuringM6)!!.toInt()
    toolStatus = toolStatus {
      pocketPrepared = byteBuffer.getIntForKey(Key.IoPocketPrepared)!!
      toolInSpindle = byteBuffer.getIntForKey(Key.IoLoadedTool)!!
    }
    coolantStatus = coolantStatus {
      mist = byteBuffer.getBooleanForKey(Key.IoCoolantMist)!!
      flood = byteBuffer.getBooleanForKey(Key.IoCoolantFlood)!!
    }
    auxStatus = auxStatus { estop = byteBuffer.getBooleanForKey(Key.IoAuxEstop)!!.toInt() }
    lubeStatus = lubeStatus {
      on = byteBuffer.getBooleanForKey(Key.IoAuxLubeOn)!!
      level = byteBuffer.getBooleanForKey(Key.IoAuxLubeLevelOk)!!.toInt()
    }
  }
}

internal fun Boolean.toInt() = if (this) 1 else 0
