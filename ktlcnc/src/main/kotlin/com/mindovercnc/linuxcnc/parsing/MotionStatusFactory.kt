package com.mindovercnc.linuxcnc.parsing

import com.mindovercnc.linuxcnc.nml.BuffDescriptor
import com.mindovercnc.linuxcnc.nml.Key
import ro.dragossusi.proto.linuxcnc.status.MotionStatus
import java.nio.ByteBuffer

private const val IO_NUMBER = 64

class MotionStatusFactory(
    descriptor: BuffDescriptor,
    private val trajectoryStatusFactory: TrajectoryStatusFactory,
    private val jointStatusFactory: JointStatusFactory,
    private val spindleStatusFactory: SpindleStatusFactory,
    private val positionFactory: PositionFactory,
) : ParsingFactory<MotionStatus>(descriptor) {

    override fun parse(byteBuffer: ByteBuffer) = MotionStatus(
        trajectory_status = trajectoryStatusFactory.parse(byteBuffer),
        joint_status = jointStatusFactory.parse(byteBuffer),
        spindle_status = spindleStatusFactory.parse(byteBuffer),
        synch_di = parseDigitalIO(byteBuffer, Key.Motion64DigitalInputsInt),
        synch_do = parseDigitalIO(byteBuffer, Key.Motion64DigitalOutputsInt),
        analog_input = parseAnalogIO(byteBuffer, Key.Motion64AnalogInputsDouble),
        analog_output = parseAnalogIO(byteBuffer, Key.Motion64AnalogOutputsDouble),
        on_soft_limit = byteBuffer.getBooleanForKey(Key.MotionOnSoftLimit)!!.toInt(),
        external_offsets_applied = byteBuffer.getBooleanForKey(Key.ExternalOffsetsApplied)!!.toInt(),
        eoffset_pose = positionFactory.parse(byteBuffer, PositionFactory.PositionType.EXTERNAL_OFFSETS),
        num_extra_joints = byteBuffer.getIntForKey(Key.NumExtraJoints)!!,
    )

    private fun parseDigitalIO(byteBuffer: ByteBuffer, key: Key): List<Int> {
        val result = mutableListOf<Int>()
        for (ioIndex in 0 until IO_NUMBER) {
            val offset = ioIndex * 4 // 4 bytes for int
            result.add(byteBuffer.getIntForKey(key, offset)!!)
        }
        return result
    }

    private fun parseAnalogIO(byteBuffer: ByteBuffer, key: Key): List<Double> {
        val result = mutableListOf<Double>()
        for (ioIndex in 0 until IO_NUMBER) {
            val offset = ioIndex * 8 // 8 bytes for double
            result.add(byteBuffer.getDoubleForKey(key, offset)!!)
        }
        return result
    }
}
