package com.mindovercnc.linuxcnc.nml

import com.mindovercnc.linuxcnc.HalHandler
import com.mindovercnc.linuxcnc.initializer.KtlCncInitializer
import com.mindovercnc.linuxcnc.model.HalPin
import kotlinx.coroutines.test.runTest
import java.io.File
import kotlin.test.Test

class CreateComponentTest {

    @Test
    internal fun `Try creating a new component`() = runTest {
        KtlCncInitializer(File(".")).initialise()
        val halHandler = HalHandler()
        val halComponent = halHandler.createComponent("WeilerE30Testsss")
        println("Component created: $halComponent")
        halComponent?.let {
            val pinJoystickXPlus = it.addPin("Pin1", HalPin.Type.BIT, HalPin.Dir.IN) as? HalPin<Boolean>
            val pinJoystickXMinus = it.addPin("Pin2", HalPin.Type.BIT, HalPin.Dir.IN) as? HalPin<Boolean>
            it.setReady(it.componentId)
        }
    }
}
