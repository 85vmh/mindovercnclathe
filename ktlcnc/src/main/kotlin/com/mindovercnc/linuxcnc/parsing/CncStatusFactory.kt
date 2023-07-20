package com.mindovercnc.linuxcnc.parsing

import com.mindovercnc.linuxcnc.nml.BuffDescriptor
import ro.dragossusi.proto.linuxcnc.CncStatus
import java.nio.ByteBuffer

class CncStatusFactory(
    descriptor: BuffDescriptor,
    private val taskStatusFactory: TaskStatusFactory,
    private val motionStatusFactory: MotionStatusFactory,
    private val ioStatusFactory: IoStatusFactory
) : ParsingFactory<CncStatus>(descriptor) {

    override fun parse(byteBuffer: ByteBuffer) = CncStatus(
        task_status = taskStatusFactory.parse(byteBuffer),
        motion_status = motionStatusFactory.parse(byteBuffer),
        io_status = ioStatusFactory.parse(byteBuffer)
    )
}
