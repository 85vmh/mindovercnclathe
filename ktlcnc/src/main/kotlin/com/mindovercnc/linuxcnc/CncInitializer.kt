package com.mindovercnc.linuxcnc

import java.io.File
import java.util.logging.Logger

object CncInitializer {

  private val LOG = Logger.getLogger("CncInitializer")
  private const val libName = "libLinuxCNC.so"

  /** Extract .so file into the destination folder */
  operator fun invoke(destFolder: File): File {
    val destFile = File(destFolder, libName)
    if (!destFile.exists()) {
      LOG.info("Copy $libName to $destFolder")

      val resourceStream = CncInitializer::class.java.classLoader.getResourceAsStream(libName)

      requireNotNull(resourceStream) { "$libName not found" }

      resourceStream.use { inputStream -> inputStream.copyTo(destFile.outputStream()) }
    }
    // System.loadLibrary("linuxcncini");
    System.loadLibrary("nml")
    System.loadLibrary("linuxcnchal")
    // Load the .so file from path in which we extracted it.
    System.load(destFile.absolutePath)
    return destFile
  }
}

fun main() {
  val file = CncInitializer.invoke(File("."))
  println(file)
}
