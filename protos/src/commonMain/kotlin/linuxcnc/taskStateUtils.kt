package linuxcnc

import ro.dragossusi.proto.linuxcnc.status.TaskState

val TaskState.stateNum: Int
    get() = value + 1
