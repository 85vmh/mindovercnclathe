package com.mindovercnc.linuxcnc.model

data class IntHalPin
constructor(
  override val componentName: String,
  override val name: String,
  override val type: HalPin.Type,
  override val dir: HalPin.Dir,
) : HalPin<Int> {

  external override fun setPinValue(value: Int)

  external override fun refreshValue(): Int
}
