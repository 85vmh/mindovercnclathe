package ro.dragossusi.proto.linuxcnc

import ro.dragossusi.proto.linuxcnc.status.JogMode

val JogMode.value: Int?
  get() =
    when (this) {
      JogMode.JOG_AXIS -> 0
      JogMode.JOG_JOINT -> 1
      JogMode.UNRECOGNIZED -> null
    }
