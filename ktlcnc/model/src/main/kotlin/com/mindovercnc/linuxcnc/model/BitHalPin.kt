package com.mindovercnc.linuxcnc.model

data class BitHalPin
constructor(
  override val componentName: String,
  override val name: String,
  override val type: HalPin.Type,
  override val dir: HalPin.Dir,
) : HalPin<Boolean> {

  external override fun setPinValue(value: Boolean)

  external override fun refreshValue(): Boolean
}
