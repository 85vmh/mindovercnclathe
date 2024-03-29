package com.mindovercnc.linuxcnc.domain

import com.mindovercnc.model.Axis
import com.mindovercnc.model.Direction
import com.mindovercnc.data.linuxcnc.model.G53AxisLimits
import com.mindovercnc.model.codegen.CodegenPoint
import kotlin.test.Test
import kotlin.test.assertEquals

class ManualTurningHelperTest {
    private val g53Limits =
        G53AxisLimits(
            xMinLimit = 0.0,
            xMaxLimit = 151.0, // x limit is in radius mode.
            zMinLimit = 0.0,
            zMaxLimit = 650.0
        )
    private val testSubject = ManualTurningHelper

    @Test
    fun testFeedXMinus() {
        val command = testSubject.getStraightTurningCommand(Axis.X, Direction.Negative, g53Limits)
        assertEquals("G53 G1 X0", command)
    }

    @Test
    fun testFeedXPlus() {
        val command = testSubject.getStraightTurningCommand(Axis.X, Direction.Positive, g53Limits)
        assertEquals("G53 G1 X302", command)
    }

    @Test
    fun testFeedZMinus() {
        val command = testSubject.getStraightTurningCommand(Axis.Z, Direction.Negative, g53Limits)
        assertEquals("G53 G1 Z0", command)
    }

    @Test
    fun testFeedZPlus() {
        val command = testSubject.getStraightTurningCommand(Axis.Z, Direction.Positive, g53Limits)
        assertEquals("G53 G1 Z650", command)
    }

    @Test
    fun testTaperXMinus() {
        val startPoint = CodegenPoint(x = 10.0, z = 10.0)
        val command =
            testSubject.getTaperTurningCommand(
                Axis.X,
                Direction.Negative,
                g53Limits,
                startPoint,
                angle = 45.0
            )
        assertEquals("G53 G1 X0 Z15", command)
    }

    @Test
    fun testTaperXPlus() {
        val startPoint = CodegenPoint(x = 10.0, z = 10.0)
        val command =
            testSubject.getTaperTurningCommand(
                Axis.X,
                Direction.Positive,
                g53Limits,
                startPoint,
                angle = 45.0
            )
        assertEquals("G53 G1 X30 Z0", command)
    }

    @Test
    fun testTaperZMinus() {
        val startPoint = CodegenPoint(x = 10.0, z = 10.0)
        val command =
            testSubject.getTaperTurningCommand(
                Axis.Z,
                Direction.Negative,
                g53Limits,
                startPoint,
                angle = 45.0
            )
        assertEquals("G53 G1 X0 Z5", command)
    }

    //    @Test
    //    fun testTaperZPlus() {
    //        val startPoint = CodegenPoint(
    //            x = 10.0,
    //            z = 10.0
    //        )
    //        val command = testSubject.getTaperTurningCommand(
    //            Axis.Z,
    //            Direction.Positive,
    //            g53Limits,
    //            startPoint,
    //            angle = 45.0
    //        )
    //        assertEquals("G53 G1 X30 Z0", command)
    //    }
}
