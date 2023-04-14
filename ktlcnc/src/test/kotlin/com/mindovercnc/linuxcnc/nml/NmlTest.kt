package com.mindovercnc.linuxcnc.nml

import java.io.File
import kotlin.test.Test

class NmlTest {

  @Test
  fun testBumpBufDesc() {
    val nativeDir = File("native")
    println(nativeDir)
    val descriptor = CheckBuffDescriptor(nativeDir)
    descriptor.check("DumpBufDesc")
  }
}
