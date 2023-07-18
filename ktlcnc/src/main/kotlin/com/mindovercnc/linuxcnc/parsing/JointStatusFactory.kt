package com.mindovercnc.linuxcnc.parsing

import com.mindovercnc.linuxcnc.nml.BuffDescriptor
import com.mindovercnc.linuxcnc.nml.Key
import ro.dragossusi.proto.linuxcnc.status.JointStatus
import java.nio.ByteBuffer

class JointStatusFactory(private val descriptor: BuffDescriptor) :
    ParsingFactory<List<JointStatus>>(descriptor) {

    override fun parse(byteBuffer: ByteBuffer): List<JointStatus> {
        val numJoints = byteBuffer.getIntForKey(Key.JointsCount)!!

        val joint0Offset = descriptor.entries[Key.Joint0]!!.startOffset
        val joint1Offset = descriptor.entries[Key.Joint1]!!.startOffset

        val result = mutableListOf<JointStatus>()
        for (jointNo in 0 until numJoints) {
            val jointOffset = jointNo * (joint1Offset - joint0Offset)
            result.add(createJointStatus(byteBuffer, jointOffset))
        }
        return result
    }

    private fun createJointStatus(byteBuffer: ByteBuffer, jointOffset: Int) = JointStatus(
        joint_type = byteBuffer.getBooleanForKey(Key.Joint0Type, jointOffset)!!,
        units = byteBuffer.getDoubleForKey(Key.Joint0Units, jointOffset)!!,
        backlash = byteBuffer.getDoubleForKey(Key.Joint0Backlash, jointOffset)!!,
        min_position_limit = byteBuffer.getDoubleForKey(Key.Joint0MinPositionLimit, jointOffset)!!,
        max_position_limit = byteBuffer.getDoubleForKey(Key.Joint0MaxPositionLimit, jointOffset)!!,
        min_following_error = byteBuffer.getDoubleForKey(Key.Joint0MinFollowingError, jointOffset)!!,
        max_following_error = byteBuffer.getDoubleForKey(Key.Joint0MaxFollowingError, jointOffset)!!,
        current_following_error = byteBuffer.getDoubleForKey(Key.Joint0FollowingErrorCurrent, jointOffset)!!,
        current_following_error_high_mark = byteBuffer.getDoubleForKey(Key.Joint0FollowingErrorHighMark, jointOffset)!!,
        commanded_output_position = byteBuffer.getDoubleForKey(Key.Joint0CommandedOutputPosition, jointOffset)!!,
        current_input_position = byteBuffer.getDoubleForKey(Key.Joint0CurrentInputPosition, jointOffset)!!,
        current_velocity = byteBuffer.getDoubleForKey(Key.Joint0CurrentVelocity, jointOffset)!! * 60,
        in_position = byteBuffer.getBooleanForKey(Key.Joint0IsInPosition, jointOffset)!!,
        homing = byteBuffer.getBooleanForKey(Key.Joint0IsHoming, jointOffset)!!,
        homed = byteBuffer.getBooleanForKey(Key.Joint0IsHomed, jointOffset)!!,
        amp_fault = byteBuffer.getBooleanForKey(Key.Joint0IsFaulted, jointOffset)!!,
        enabled = byteBuffer.getBooleanForKey(Key.Joint0IsEnabled, jointOffset)!!,
        min_soft_limit_exceeded = byteBuffer.getBooleanForKey(Key.Joint0IsMinSoftLimitReached, jointOffset)!!,
        max_soft_limit_exceeded = byteBuffer.getBooleanForKey(Key.Joint0IsMaxSoftLimitReached, jointOffset)!!,
        min_hard_limit_exceeded = byteBuffer.getBooleanForKey(Key.Joint0IsMinHardLimitReached, jointOffset)!!,
        max_hard_limit_exceeded = byteBuffer.getBooleanForKey(Key.Joint0IsMaxHardLimitReached, jointOffset)!!,
        overriding_limits = byteBuffer.getBooleanForKey(Key.Joint0IsLimitOverrideOn, jointOffset)!!,
    )
}
