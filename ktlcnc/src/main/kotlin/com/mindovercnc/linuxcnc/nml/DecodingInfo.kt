package com.mindovercnc.linuxcnc.nml

data class DecodingInfo(val startOffset: Int, val dataType: DataType) {
  enum class DataType(val numberOfBytes: Int) {
    Byte(1),
    Integer(4),
    Double(8),
    String(255),
    Object(-1)
  }
}
