package com.mindovercnc.linuxcnc.initializer

import com.mindovercnc.linuxcnc.KtlCncInitializer
import initializer.InitializerStep
import mu.KotlinLogging
import java.io.File

class LoadBufferDescriptorStep(
    private val destFolder: File,
    private val fileName: String
) : InitializerStep {
    override suspend fun initialise() {
        // Check if the .json file already exists in destination folder.
        val jsonDestinationFile = File(destFolder, fileName)
        if (!jsonDestinationFile.exists()) {
            LOG.info("Copy $fileName to $destFolder")
            val resourceStream = KtlCncInitializer::class.java.classLoader.getResourceAsStream(fileName)

            requireNotNull(resourceStream) { "$fileName not found" }

            resourceStream.use { inputStream -> inputStream.copyTo(jsonDestinationFile.outputStream()) }
        }
    }

    companion object {
        private val LOG = KotlinLogging.logger("LoadBufferDescriptor")
    }
}