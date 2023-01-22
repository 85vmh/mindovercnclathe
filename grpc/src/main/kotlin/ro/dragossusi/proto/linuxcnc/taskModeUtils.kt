package ro.dragossusi.proto.linuxcnc

import ro.dragossusi.proto.linuxcnc.status.TaskMode

val TaskMode.mode: Int?
  get() =
    when (this) {
      TaskMode.TaskModeManual -> 1
      TaskMode.TaskModeAuto -> 2
      TaskMode.TaskModeMDI -> 3
      TaskMode.UNRECOGNIZED -> null
    }
