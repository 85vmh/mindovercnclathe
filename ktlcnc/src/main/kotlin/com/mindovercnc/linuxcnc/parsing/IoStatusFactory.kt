package com.mindovercnc.linuxcnc.parsing

import com.mindovercnc.linuxcnc.nml.BuffDescriptor
import com.mindovercnc.linuxcnc.nml.Key
import ro.dragossusi.proto.linuxcnc.status.*
import java.nio.ByteBuffer

class IoStatusFactory(descriptor: BuffDescriptor) : ParsingFactory<IoStatus>(descriptor) {

    override fun parse(byteBuffer: ByteBuffer) = IoStatus(
        cycle_time = byteBuffer.getDoubleForKey(Key.IoCycleTime)!!,
        reason = byteBuffer.getIntForKey(Key.IoReason)!!,
        fault = byteBuffer.getBooleanForKey(Key.IoFaultDuringM6)!!.toInt(),
        tool_status = ToolStatus(
            pocket_prepared = byteBuffer.getIntForKey(Key.IoPocketPrepared)!!,
            tool_in_spindle = byteBuffer.getIntForKey(Key.IoLoadedTool)!!
        ),
        coolant_status = CoolantStatus(
            mist = byteBuffer.getBooleanForKey(Key.IoCoolantMist)!!,
            flood = byteBuffer.getBooleanForKey(Key.IoCoolantFlood)!!
        ),
        aux_status = AuxStatus(estop = byteBuffer.getBooleanForKey(Key.IoAuxEstop)!!.toInt()),
        lube_status = LubeStatus(
            on = byteBuffer.getBooleanForKey(Key.IoAuxLubeOn)!!,
            level = byteBuffer.getBooleanForKey(Key.IoAuxLubeLevelOk)!!.toInt()
        ),
    )
}

internal fun Boolean.toInt() = if (this) 1 else 0
