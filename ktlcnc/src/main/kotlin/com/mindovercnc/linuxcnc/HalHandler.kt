package com.mindovercnc.linuxcnc

import com.mindovercnc.linuxcnc.model.HalComponent

class HalHandler {
    external fun createComponent(name: String): HalComponent?
}
