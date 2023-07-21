package com.mindovercnc.linuxcnc

import java.io.File
import java.util.logging.Logger

object CncInitializer {

    private val LOG = Logger.getLogger("CncInitializer")
    private const val linuxcncLibName = "libLinuxCNC.so"
    private const val bufferDescriptor = "bufferDescriptor.json"

    /** Extract .so file into the destination folder */
    operator fun invoke(destFolder: File): File {
        // Check if the .so file already exists in destination folder.
        val libDestinationFile = File(destFolder, linuxcncLibName)
        if (!libDestinationFile.exists()) {
            LOG.info("Copy $linuxcncLibName to $destFolder")

            val resourceStream = CncInitializer::class.java.classLoader.getResourceAsStream(linuxcncLibName)

            requireNotNull(resourceStream) { "$linuxcncLibName not found" }

            resourceStream.use { inputStream -> inputStream.copyTo(libDestinationFile.outputStream()) }
        }

        // Check if the .json file already exists in destination folder.
        val jsonDestinationFile = File(destFolder, bufferDescriptor)
        if (!jsonDestinationFile.exists()) {
            LOG.info("Copy $bufferDescriptor to $destFolder")
            val resourceStream = CncInitializer::class.java.classLoader.getResourceAsStream(bufferDescriptor)

            requireNotNull(resourceStream) { "$bufferDescriptor not found" }

            resourceStream.use { inputStream -> inputStream.copyTo(jsonDestinationFile.outputStream()) }
        }

        // System.loadLibrary("linuxcncini");
        System.loadLibrary("nml")
        System.loadLibrary("linuxcnchal")
        // Load the .so file from path in which we extracted it.
        System.load(libDestinationFile.absolutePath)
        return libDestinationFile
    }
}

fun main() {
    val file = CncInitializer.invoke(File("."))
    println(file)
}
