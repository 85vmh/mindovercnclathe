package linuxcnc

import ro.dragossusi.proto.linuxcnc.status.IoStatus

// todo check this
val IoStatus.currentToolNo
    get() = tool_status?.tool_in_spindle
