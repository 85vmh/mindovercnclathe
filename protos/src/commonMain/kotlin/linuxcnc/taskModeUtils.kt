package linuxcnc

import ro.dragossusi.proto.linuxcnc.status.TaskMode

val TaskMode.mode: Int
    get() = value + 1
