package com.mindovercnc.linuxcnc.model

data class FloatHalPin
constructor(
  override val componentName: String,
  override val name: String,
  override val type: HalPin.Type,
  override val dir: HalPin.Dir,
) : HalPin<Float> {

  external override fun setPinValue(value: Float)

  external override fun refreshValue(): Float
}
