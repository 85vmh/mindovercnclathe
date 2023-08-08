package com.mindovercnc.linuxcnc

import com.mindovercnc.linuxcnc.initializer.LoadBufferDescriptorStep
import com.mindovercnc.linuxcnc.initializer.LoadSoResourceStep
import initializer.InitializerStep
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import java.io.File

class KtlCncInitializer(private val destFolder: File) : InitializerStep {

    /** Extract .so file into the destination folder */
    override suspend fun initialise() {
        LOG.info("Loading buffer descriptor")
        LoadBufferDescriptorStep(destFolder, bufferDescriptor)

        // System.loadLibrary("linuxcncini");
        System.loadLibrary("nml")
        System.loadLibrary("linuxcnchal")

        LOG.info { "Loading $linuxcncLibName" }
        LoadSoResourceStep(destFolder, linuxcncLibName).initialise()
    }

    companion object {
        private val LOG = KotlinLogging.logger("CncInitializer")
        private const val linuxcncLibName = "libLinuxCNC.so"
        private const val bufferDescriptor = "bufferDescriptor.json"
    }
}

fun main() {
    val file = runBlocking {
        KtlCncInitializer(File(".")).initialise()
    }
    println(file)
}
