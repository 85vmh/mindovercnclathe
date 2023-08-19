package com.mindovercnc.linuxcnc.di

import com.mindovercnc.linuxcnc.ErrorReader
import com.mindovercnc.linuxcnc.nml.BuffDescriptor
import com.mindovercnc.linuxcnc.nml.BuffDescriptorV29
import com.mindovercnc.linuxcnc.nml.DynamicBuffDescriptor
import com.mindovercnc.linuxcnc.nml.MemoryEntries
import com.mindovercnc.linuxcnc.parsing.*
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import okio.Path.Companion.toPath
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val ParseFactoryModule =
    DI.Module("parseFactory") {
        bindSingleton { PositionFactory(instance()) }
        bindSingleton { TaskStatusFactory(instance(), instance()) }
        bindSingleton { TrajectoryStatusFactory(instance(), instance()) }
        bindSingleton { JointStatusFactory(instance()) }
        bindSingleton { SpindleStatusFactory(instance()) }
        bindSingleton {
            MotionStatusFactory(instance(), instance(), instance(), instance(), instance())
        }
        bindSingleton { IoStatusFactory(instance()) }
        bindSingleton { CncStatusFactory(instance(), instance(), instance(), instance()) }
        bindSingleton { ErrorReader() }
    }

val KtLcncModule = DI.Module("ktlcnc") {
    bindSingleton<BuffDescriptor> {

        //TODO: I should be taking this somehow from the
        val homeFile = System.getProperty("user.home").toPath()
        val appDir = homeFile.div(".mindovercnclathe")

        val file = appDir.div("bufferDescriptor.json").toFile()

        if (!file.exists()) {
            LOG.info("Buff Descriptor file not found, use the default one")
            return@bindSingleton BuffDescriptorV29()
        } else {
            val contents = file.readText()
            val memoryEntries = Json.decodeFromString<MemoryEntries>(contents)
            LOG.info("Dynamic Buff Descriptor loaded from file: $file")
            DynamicBuffDescriptor(memoryEntries)
        }
    }
}

private val LOG = KotlinLogging.logger("KtlCnc")