package com.mindovercnc.repository

import ro.dragossusi.proto.linuxcnc.status.JogMode
import ro.dragossusi.proto.linuxcnc.status.TaskMode
import ro.dragossusi.proto.linuxcnc.status.TaskState

/** Repository for running CNC commands. */
interface CncCommandRepository {

  /**
   * Set the task mode.
   *
   * @param taskMode The [TaskMode] to be set.
   */
  fun setTaskMode(taskMode: TaskMode)

  /**
   * Set the task state.
   *
   * @param taskState The [TaskState] to be set.
   */
  fun setTaskState(taskState: TaskState)

  fun taskAbort()

  fun homeAll()

  fun homeAxis(jointNumber: Int)

  fun overrideLimits(jointNumber: Int)

  fun unHomeAll()

  fun unHomeAxis(jointNumber: Int)

  fun setFeedHold(hold: Boolean)

  fun setFeedOverride(double: Double)

  fun runProgram()

  fun pauseProgram()

  fun resumeProgram()

  fun setTeleopEnable(enabled: Boolean)

  fun jogContinuous(jogMode: JogMode = JogMode.JOG_AXIS, axisOrJoint: Int, speed: Double)

  fun jogIncremental(
    jogMode: JogMode = JogMode.JOG_AXIS,
    axisOrJoint: Int,
    stepSize: Double,
    speed: Double
  )

  fun jogAbsolute(
    jogMode: JogMode = JogMode.JOG_AXIS,
    axisOrJoint: Int,
    position: Double,
    speed: Double
  )

  fun jogStop(jogMode: JogMode = JogMode.JOG_AXIS, axisOrJoint: Int)

  /**
   * Warning: this does stop the joint at the specified limit, but it errors out when reaching it,
   * so the machine needs to be re-homed.
   */
  fun setMinPositionLimit(jointNumber: Int, limit: Double)

  /**
   * Warning: this does stop the joint at the specified limit, but it errors out when reaching it,
   * so the machine needs to be re-homed.
   */
  fun setMaxPositionLimit(jointNumber: Int, limit: Double)

  fun setBacklash(jointNumber: Int, backlash: Double)

  fun executeMdiCommand(command: String): Boolean

  fun loadProgramFile(filePath: String)
  fun stepProgram()
  fun stopProgram()
}
