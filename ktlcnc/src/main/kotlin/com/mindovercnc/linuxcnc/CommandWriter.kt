package com.mindovercnc.linuxcnc

/*
 * **************************************************************************
 *
 *  file:       com.mindovercnc.linuxcnc.CommandWriter.java
 *  project:    GUI for linuxcnc
 *  subproject: graphical application frontend
 *  purpose:    create a smart application, that assists in managing
 *              control of cnc-machines
 *  created:    15.10.2019 by Django Reinhard
 *  copyright:  all rights reserved
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * **************************************************************************
 */
class CommandWriter {
    private external fun init(): Int
    external fun setTaskMode(mode: Int)
    external fun setTaskState(state: Int)
    external fun taskAbort()
    external fun homeAxis(jointNum: Int)
    external fun unHomeAxis(jointNum: Int)
    external fun overrideLimits(jointNum: Int)

    /** 0 = joint 1 = teleop */
    external fun setMotionMode(motionMode: Int)

    /** jogMode = 0 - jog axis jogMode = 1 - jog joint */
    external fun jogContinuous(jogMode: Int, axisOrJoint: Int, speed: Double)

    /** jogMode = 0 - jog axis jogMode = 1 - jog joint */
    external fun jogIncremental(jogMode: Int, axisOrJoint: Int, stepSize: Double, speed: Double)

    /** jogMode = 0 - jog axis jogMode = 1 - jog joint */
    external fun jogAbsolute(jogMode: Int, axisOrJoint: Int, position: Double, speed: Double)
    external fun jogStop(jogMode: Int, axisOrJoint: Int)

    external fun setMinPositionLimit(jointNum: Int, limit: Double)
    external fun setMaxPositionLimit(jointNum: Int, limit: Double)
    external fun setBacklash(jointNum: Int, backlash: Double)

    /** 0 - disable 1 - enable */
    external fun setFeedHold(mode: Int)
    external fun loadTaskPlan(fileName: String?)
    external fun loadToolTable(fileName: String?)
    external fun sendMDICommand(command: String): Boolean
    external fun setAuto(autoMode: Int, fromLine: Int = 0)
    external fun setBlockDelete(enable: Boolean)
    external fun setFeedOverride(rate: Double)
    external fun setFlood(enable: Boolean)
    external fun setMist(enable: Boolean)
    external fun setOptionalStop(stop: Boolean)
    external fun setRapidOverride(rate: Double)
    external fun setSpindle(enable: Boolean, speed: Int, direction: Int)
    external fun setSpindleOverride(rate: Double)

    init {
        init()
    }
}
