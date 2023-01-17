package ro.dragossusi.proto.linuxcnc

import ro.dragossusi.proto.linuxcnc.status.TaskState

val TaskState.stateNum: Int?
  get() =
    when (this) {
      TaskState.EStop -> 1
      TaskState.EStopReset -> 2
      TaskState.MachineOff -> 3
      TaskState.MachineOn -> 4
      TaskState.UNRECOGNIZED -> null
    }
