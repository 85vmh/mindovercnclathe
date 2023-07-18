package com.mindovercnc.linuxcnc.parsing

import com.mindovercnc.linuxcnc.nml.BuffDescriptor
import com.mindovercnc.linuxcnc.nml.Key
import ro.dragossusi.proto.linuxcnc.status.SpindleDirection
import ro.dragossusi.proto.linuxcnc.status.SpindleStatus
import java.nio.ByteBuffer

class SpindleStatusFactory(private val descriptor: BuffDescriptor) :
    ParsingFactory<List<SpindleStatus>>(descriptor) {

    override fun parse(byteBuffer: ByteBuffer): List<SpindleStatus> {
        val numSpindles = byteBuffer.getIntForKey(Key.SpindlesCount)!!

        val spindle0Offset = descriptor.entries[Key.Spindle0]!!.startOffset
        val spindle1Offset = descriptor.entries[Key.Spindle1]!!.startOffset

        val result = mutableListOf<SpindleStatus>()
        for (spindleNo in 0 until numSpindles) {
            val spindleOffset = spindleNo * (spindle1Offset - spindle0Offset)
            result.add(createSpindleStatus(byteBuffer, spindleOffset))
        }
        return result
    }

    private fun createSpindleStatus(byteBuffer: ByteBuffer, spindleOffset: Int) =
        SpindleStatus(
            speed = byteBuffer.getDoubleForKey(Key.Spindle0Speed, spindleOffset)!!,
            spindle_scale = byteBuffer.getDoubleForKey(Key.Spindle0Scale, spindleOffset)!! * 100,
            css_maximum = byteBuffer.getDoubleForKey(Key.Spindle0CssMaximum, spindleOffset)!!,
            css_factor =
            byteBuffer.getDoubleForKey(Key.Spindle0CssFactor, spindleOffset)!!, // TODO: maybe *100
            state = byteBuffer.getIntForKey(Key.Spindle0State, spindleOffset)!!,
            direction =
            SpindleDirection.fromValue(
                byteBuffer.getIntForKey(Key.Spindle0Direction, spindleOffset)!!
            )!!,
            increasing = byteBuffer.getIntForKey(Key.Spindle0Increasing, spindleOffset)!!,
            brake = byteBuffer.getBooleanForKey(Key.Spindle0Brake, spindleOffset)!!.toInt(),
            enabled = byteBuffer.getBooleanForKey(Key.Spindle0Enabled, spindleOffset)!!,
            spindle_override_enabled = byteBuffer.getBooleanForKey(
                Key.Spindle0OverrideEnabled,
                spindleOffset
            )!!,
            homed = byteBuffer.getBooleanForKey(Key.Spindle0Homed, spindleOffset)!!,
            orient_state = byteBuffer.getIntForKey(Key.Spindle0OrientState, spindleOffset)!!,
            orient_fault = byteBuffer.getIntForKey(Key.Spindle0OrientFault, spindleOffset)!!
        )
}
