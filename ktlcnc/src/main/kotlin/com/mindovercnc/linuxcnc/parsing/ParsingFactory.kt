package com.mindovercnc.linuxcnc.parsing

import com.mindovercnc.linuxcnc.nml.BuffDescriptor
import com.mindovercnc.linuxcnc.nml.DecodingInfo
import com.mindovercnc.linuxcnc.nml.Key
import java.nio.ByteBuffer

abstract class ParsingFactory<T>(private val buffDescriptor: BuffDescriptor) {
  abstract fun parse(byteBuffer: ByteBuffer): T

  internal fun ByteBuffer.getIntForKey(key: Key, extraOffset: Int = 0): Int? {
    return buffDescriptor.entries[key]?.let {
      when (it.dataType) {
        DecodingInfo.DataType.Integer,
        DecodingInfo.DataType.Object -> this.getInt(it.startOffset + extraOffset)
        else -> null
      }
    }
  }

  internal fun ByteBuffer.getDoubleForKey(key: Key, extraOffset: Int = 0): Double? {
    return buffDescriptor.entries[key]?.let {
      when (it.dataType) {
        DecodingInfo.DataType.Double,
        DecodingInfo.DataType.Object -> this.getDouble(it.startOffset + extraOffset)
        else -> null
      }
    }
  }

  internal fun ByteBuffer.getBooleanForKey(key: Key, extraOffset: Int = 0): Boolean? {
    return buffDescriptor.entries[key]?.let {
      when (it.dataType) {
        DecodingInfo.DataType.Byte -> this.get(it.startOffset + extraOffset) != 0.toByte()
        DecodingInfo.DataType.Integer -> this.getInt(it.startOffset + extraOffset) != 0
        else -> null
      }
    }
  }

  internal fun ByteBuffer.getStringForKey(key: Key): String? {
    return buffDescriptor.entries[key]?.let {
      when (it.dataType) {
        DecodingInfo.DataType.String -> {
          //                    val byteArray = ByteArray()
          //                    this.get(byteArray, it.startOffset, 255)
          ""
        }
        else -> null
      }
    }
  }
}
