package com.mindovercnc.linuxcnc.parsing

import com.mindovercnc.linuxcnc.nml.BuffDescriptor
import com.mindovercnc.linuxcnc.nml.Key
import java.nio.ByteBuffer
import ro.dragossusi.proto.linuxcnc.status.MotionStatus
import ro.dragossusi.proto.linuxcnc.status.motionStatus

private const val IO_NUMBER = 64

class MotionStatusFactory(
  descriptor: BuffDescriptor,
  private val trajectoryStatusFactory: TrajectoryStatusFactory,
  private val jointStatusFactory: JointStatusFactory,
  private val spindleStatusFactory: SpindleStatusFactory,
  private val positionFactory: PositionFactory,
) : ParsingFactory<MotionStatus>(descriptor) {

  override fun parse(byteBuffer: ByteBuffer): MotionStatus {
    return motionStatus {
      trajectoryStatus = trajectoryStatusFactory.parse(byteBuffer)
      jointStatus.addAll(jointStatusFactory.parse(byteBuffer))
      spindleStatus.addAll(spindleStatusFactory.parse(byteBuffer))
      synchDi.addAll(parseDigitalIO(byteBuffer, Key.Motion64DigitalInputsInt))
      synchDo.addAll(parseDigitalIO(byteBuffer, Key.Motion64DigitalOutputsInt))
      analogInput.addAll(parseAnalogIO(byteBuffer, Key.Motion64AnalogInputsDouble))
      analogOutput.addAll(parseAnalogIO(byteBuffer, Key.Motion64AnalogOutputsDouble))
      onSoftLimit = byteBuffer.getBooleanForKey(Key.MotionOnSoftLimit)!!.toInt()
      externalOffsetsApplied = byteBuffer.getBooleanForKey(Key.ExternalOffsetsApplied)!!.toInt()
      eoffsetPose = positionFactory.parse(byteBuffer, PositionFactory.PositionType.EXTERNAL_OFFSETS)
      numExtraJoints = byteBuffer.getIntForKey(Key.NumExtraJoints)!!
    }
  }

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
