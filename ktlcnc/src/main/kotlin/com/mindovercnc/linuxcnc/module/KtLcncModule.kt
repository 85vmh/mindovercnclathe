package com.mindovercnc.linuxcnc.module

import com.mindovercnc.linuxcnc.nml.BuffDescriptor
import com.mindovercnc.linuxcnc.nml.BuffDescriptorV29
import com.mindovercnc.linuxcnc.nml.DynamicBuffDescriptor
import com.mindovercnc.linuxcnc.nml.MemoryEntries
import kotlinx.serialization.json.Json
import okio.Path.Companion.toPath
import org.kodein.di.DI
import org.kodein.di.bindSingleton

val KtLcncModule = DI.Module("ktlcnc") {
    bindSingleton<BuffDescriptor> {

        //TODO: I should be taking this somehow from the
        val homeFile = System.getProperty("user.home").toPath()
        val appDir = homeFile.div(".mindovercnclathe")

        val file = appDir.div("bufferDescriptor.json").toFile()

        if (!file.exists()) {
            println("Buff Descriptor file not found, use the default one")
            return@bindSingleton BuffDescriptorV29()
        } else {
            val contents = file.readText()
            val memoryEntries = Json.decodeFromString<MemoryEntries>(contents)
            println("Dynamic Buff Descriptor loaded from file: $file")
            DynamicBuffDescriptor(memoryEntries)
        }
    }
}