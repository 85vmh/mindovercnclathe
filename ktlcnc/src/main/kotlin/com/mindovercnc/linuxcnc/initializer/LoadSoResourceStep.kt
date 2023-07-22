package com.mindovercnc.linuxcnc.initializer

import com.mindovercnc.linuxcnc.KtlCncInitializer
import initializer.InitializerStep
import mu.KotlinLogging
import java.io.File

class LoadSoResourceStep(
    private val destFolder: File,
    private val libName: String
) : InitializerStep {
    override suspend fun initialise() {
        // Check if the .so file already exists in destination folder.
        val libDestinationFile = File(destFolder, libName)
        if (!libDestinationFile.exists()) {
            LOG.info("Copy $libName to $destFolder")

            val resourceStream = KtlCncInitializer::class.java.classLoader.getResourceAsStream(libName)

            requireNotNull(resourceStream) { "$libName not found" }
                .use { inputStream -> inputStream.copyTo(libDestinationFile.outputStream()) }
        } else {
            LOG.info { "$libName already extracted" }
        }

        // Load the .so file from path in which we extracted it.
        System.load(libDestinationFile.absolutePath)
    }

    companion object {
        private val LOG = KotlinLogging.logger("LoadSoResource")
    }
}