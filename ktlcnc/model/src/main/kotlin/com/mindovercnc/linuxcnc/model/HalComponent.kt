package com.mindovercnc.linuxcnc.model

class HalComponent internal constructor(var name: String) {
  var componentId = 0

  external fun addPin(name: String, type: HalPin.Type, dir: HalPin.Dir): HalPin<*>?

  external fun setReady(componentId: Int)

  override fun toString(): String {
    return "HalComponent{" + "componentId=" + componentId + ", name='" + name + '\'' + '}'
  }
}
