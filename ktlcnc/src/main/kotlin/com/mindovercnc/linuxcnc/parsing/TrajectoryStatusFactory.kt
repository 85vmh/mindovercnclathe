package com.mindovercnc.linuxcnc.parsing

import com.mindovercnc.linuxcnc.nml.BuffDescriptor
import com.mindovercnc.linuxcnc.nml.Key
import java.nio.ByteBuffer
import ro.dragossusi.proto.linuxcnc.status.TrajectoryStatus
import ro.dragossusi.proto.linuxcnc.status.trajectoryStatus

class TrajectoryStatusFactory(
  descriptor: BuffDescriptor,
  private val positionFactory: PositionFactory,
) : ParsingFactory<TrajectoryStatus>(descriptor) {

  override fun parse(byteBuffer: ByteBuffer): TrajectoryStatus {
    return trajectoryStatus {
      linearUnits = byteBuffer.getDoubleForKey(Key.LinearUnits)!!
      angularUnits = byteBuffer.getDoubleForKey(Key.AngularUnits)!!
      cycleTime = byteBuffer.getDoubleForKey(Key.CycleTimeSeconds)!!
      joints = byteBuffer.getIntForKey(Key.JointsCount)!!
      spindles = byteBuffer.getIntForKey(Key.SpindlesCount)!!
      axisMask = byteBuffer.getIntForKey(Key.AxisMask)!!
      //            motionMode = MotionMode.forNumber(byteBuffer.getIntForKey(Key.MotionMode)!!)!!
      enabled = byteBuffer.getBooleanForKey(Key.IsEnabled)!!
      inPosition = byteBuffer.getBooleanForKey(Key.IsInPosition)!!
      queue = byteBuffer.getIntForKey(Key.PendingMotions)!!
      activeQueue = byteBuffer.getIntForKey(Key.ActiveMotions)!!
      queueFull = byteBuffer.getBooleanForKey(Key.IsMotionQueueFull)!!
      //            currentMotionId = byteBuffer.getIntForKey(Key.CurrentMotionId)!!
      paused = byteBuffer.getBooleanForKey(Key.IsMotionPaused)!!
      scale = byteBuffer.getDoubleForKey(Key.VelocityScaleFactor)!! * 100
      rapidScale = byteBuffer.getDoubleForKey(Key.RapidScaleFactor)!! * 100
      //            currentCommandedPosition = positionFactory.parse(byteBuffer,
      // PositionFactory.PositionType.CURRENT_COMMANDED),
      actualPosition =
        positionFactory.parse(byteBuffer, PositionFactory.PositionType.CURRENT_ACTUAL)
      velocity = byteBuffer.getDoubleForKey(Key.SystemVelocity)!!
      acceleration = byteBuffer.getDoubleForKey(Key.SystemAcceleration)!!
      maxVelocity = byteBuffer.getDoubleForKey(Key.MaxVelocity)!! * 60
      maxAcceleration = byteBuffer.getDoubleForKey(Key.MaxAcceleration)!!
      probedPosition = positionFactory.parse(byteBuffer, PositionFactory.PositionType.PROBED)
      probeTripped = byteBuffer.getBooleanForKey(Key.IsProbeTripped)!!
      probing = byteBuffer.getBooleanForKey(Key.IsProbing)!!
      probeval = byteBuffer.getIntForKey(Key.ProbeInputValue)!!
      kinematicsType = byteBuffer.getIntForKey(Key.KinematicsType)!!
      motionType = byteBuffer.getIntForKey(Key.MotionType)!!
      distanceToGo = byteBuffer.getDoubleForKey(Key.CurrentMoveDtg)!!
      position = positionFactory.parse(byteBuffer, PositionFactory.PositionType.DTG)
      //            currentVelocity = byteBuffer.getDoubleForKey(Key.CurrentMoveVelocity)!! * 60,
      feedOverrideEnabled = byteBuffer.getBooleanForKey(Key.IsFeedOverrideEnabled)!!
      adaptiveFeedEnabled = byteBuffer.getBooleanForKey(Key.IsAdaptiveFeedEnabled)!!
      feedHoldEnabled = byteBuffer.getBooleanForKey(Key.IsFeedHoldEnabled)!!
    }
  }
}
