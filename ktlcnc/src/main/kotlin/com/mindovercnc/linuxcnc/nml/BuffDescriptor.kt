package com.mindovercnc.linuxcnc.nml

interface BuffDescriptor {
  val entries: Map<Key, DecodingInfo>
}
