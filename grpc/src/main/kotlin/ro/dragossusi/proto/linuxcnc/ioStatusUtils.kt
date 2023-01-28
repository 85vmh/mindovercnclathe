package ro.dragossusi.proto.linuxcnc

import ro.dragossusi.proto.linuxcnc.status.IoStatus

// todo check this
val IoStatus.currentToolNo
  get() = toolStatus.toolInSpindle
