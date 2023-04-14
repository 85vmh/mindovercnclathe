package com.mindovercnc.linuxcnc.model

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

interface HalPin<T> {
  val componentName: String
  val name: String
  val type: Type
  val dir: Dir

  fun setPinValue(value: T)

  fun refreshValue(): T

  fun valueFlow(refreshRate: Long) = flow {
    while (true) {
      emit(refreshValue())
      delay(refreshRate)
    }
  }

  enum class Type(val value: Int) {
    TYPE_UNSPECIFIED(-1),
    TYPE_UNINITIALIZED(0),
    BIT(1),
    FLOAT(2),
    S32(3),
    U32(4),
    PORT(5)
  }

  enum class Dir(val value: Int) {
    DIR_UNSPECIFIED(-1),
    IN(16),
    OUT(32),
    IO(16 or 32)
  }

  // These static methods are called from the native layer in order to create the pin
  companion object {
    @JvmStatic
    fun bit(compName: String, name: String, dir: Dir) = BitHalPin(compName, name, Type.BIT, dir)

    @JvmStatic
    fun float(compName: String, name: String, dir: Dir) =
      FloatHalPin(compName, name, Type.FLOAT, dir)

    @JvmStatic
    fun s32(compName: String, name: String, dir: Dir) = IntHalPin(compName, name, Type.S32, dir)

    //        @JvmStatic fun u32(name: String, dir: Dir, value: UInt) = HalPin(name, Type.U32, dir,
    // value)
  }
}
