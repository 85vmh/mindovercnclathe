package com.mindovercnc.linuxcnc.nml

import com.mindovercnc.linuxcnc.initializer.KtlCncInitializer
import kotlinx.coroutines.runBlocking
import java.io.File
import kotlin.test.Test

class KtlCncInitializerTest {
    @Test
    fun test() {
        val file = runBlocking {
            KtlCncInitializer(File(".")).initialise()
        }
        println(file)
    }
}