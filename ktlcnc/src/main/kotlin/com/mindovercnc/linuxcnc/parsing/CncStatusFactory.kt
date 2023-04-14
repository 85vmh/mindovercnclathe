package com.mindovercnc.linuxcnc.parsing

import com.mindovercnc.linuxcnc.nml.BuffDescriptor
import java.nio.ByteBuffer
import ro.dragossusi.proto.linuxcnc.CncStatus
import ro.dragossusi.proto.linuxcnc.cncStatus

class CncStatusFactory(
  descriptor: BuffDescriptor,
  private val taskStatusFactory: TaskStatusFactory,
  private val motionStatusFactory: MotionStatusFactory,
  private val ioStatusFactory: IoStatusFactory
) : ParsingFactory<CncStatus>(descriptor) {

  override fun parse(byteBuffer: ByteBuffer) = cncStatus {
    taskStatus = taskStatusFactory.parse(byteBuffer)
    motionStatus = motionStatusFactory.parse(byteBuffer)
    ioStatus = ioStatusFactory.parse(byteBuffer)
  }
}
