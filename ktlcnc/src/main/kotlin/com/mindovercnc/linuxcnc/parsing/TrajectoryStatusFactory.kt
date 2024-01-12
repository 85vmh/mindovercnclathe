package com.mindovercnc.linuxcnc.parsing

import com.mindovercnc.linuxcnc.nml.BuffDescriptor
import com.mindovercnc.linuxcnc.nml.Key
import ro.dragossusi.proto.linuxcnc.status.TrajectoryStatus
import java.nio.ByteBuffer

class TrajectoryStatusFactory(
    descriptor: BuffDescriptor,
    private val positionFactory: PositionFactory,
) : ParsingFactory<TrajectoryStatus>(descriptor) {

    override fun parse(byteBuffer: ByteBuffer): TrajectoryStatus {
        return TrajectoryStatus(
            linear_units = byteBuffer.getDoubleForKey(Key.LinearUnits)!!,
            angular_units = byteBuffer.getDoubleForKey(Key.AngularUnits)!!,
            cycle_time = byteBuffer.getDoubleForKey(Key.CycleTimeSeconds)!!,
            joints = byteBuffer.getIntForKey(Key.JointsCount)!!,
            spindles = byteBuffer.getIntForKey(Key.SpindlesCount)!!,
            axis_mask = byteBuffer.getIntForKey(Key.AxisMask)!!,
            //            motionMode = MotionMode.forNumber(byteBuffer.getIntForKey(Key.MotionMode)!!)!!
            enabled = byteBuffer.getBooleanForKey(Key.IsEnabled)!!,
            in_position = byteBuffer.getBooleanForKey(Key.IsInPosition)!!,
            queue = byteBuffer.getIntForKey(Key.PendingMotions)!!,
            active_queue = byteBuffer.getIntForKey(Key.ActiveMotions)!!,
            queue_full = byteBuffer.getBooleanForKey(Key.IsMotionQueueFull)!!,
            //            currentMotionId = byteBuffer.getIntForKey(Key.CurrentMotionId)!!
            paused = byteBuffer.getBooleanForKey(Key.IsMotionPaused)!!,
            scale = byteBuffer.getDoubleForKey(Key.VelocityScaleFactor)!! * 100,
            rapid_scale = byteBuffer.getDoubleForKey(Key.RapidScaleFactor)!! * 100,
            //            currentCommandedPosition = positionFactory.parse(byteBuffer,
            // PositionFactory.PositionType.CURRENT_COMMANDED),
            actual_position = positionFactory.parse(byteBuffer, PositionFactory.PositionType.CURRENT_ACTUAL),
            velocity = byteBuffer.getDoubleForKey(Key.SystemVelocity)!!,
            acceleration = byteBuffer.getDoubleForKey(Key.SystemAcceleration)!!,
            max_velocity = byteBuffer.getDoubleForKey(Key.MaxVelocity)!! * 60,
            max_acceleration = byteBuffer.getDoubleForKey(Key.MaxAcceleration)!!,
            probed_position = positionFactory.parse(
                byteBuffer, PositionFactory.PositionType.PROBED,
            ),
            probe_tripped = byteBuffer.getBooleanForKey(Key.IsProbeTripped)!!,
            probing = byteBuffer.getBooleanForKey(Key.IsProbing)!!,
            probeval = byteBuffer.getIntForKey(Key.ProbeInputValue)!!,
            kinematics_type = byteBuffer.getIntForKey(Key.KinematicsType)!!,
            motion_type = byteBuffer.getIntForKey(Key.MotionType)!!,
            distance_to_go = byteBuffer.getDoubleForKey(Key.CurrentMoveDtg)!!,
            position = positionFactory.parse(byteBuffer, PositionFactory.PositionType.DTG),
            dtg = positionFactory.parse(byteBuffer, PositionFactory.PositionType.DTG),
            //            currentVelocity = byteBuffer.getDoubleForKey(Key.CurrentMoveVelocity)!! * 60,
            feed_override_enabled = byteBuffer.getBooleanForKey(Key.IsFeedOverrideEnabled)!!,
            adaptive_feed_enabled = byteBuffer.getBooleanForKey(Key.IsAdaptiveFeedEnabled)!!,
            feed_hold_enabled = byteBuffer.getBooleanForKey(Key.IsFeedHoldEnabled)!!,
        )
    }
}
