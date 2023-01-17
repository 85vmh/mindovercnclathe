package com.mindovercnc.linuxcnc

import com.mindovercnc.repository.CncCommandRepository
import mu.KotlinLogging
import ro.dragossusi.proto.linuxcnc.mode
import ro.dragossusi.proto.linuxcnc.stateNum
import ro.dragossusi.proto.linuxcnc.status.JogMode
import ro.dragossusi.proto.linuxcnc.status.TaskMode
import ro.dragossusi.proto.linuxcnc.status.TaskState
import ro.dragossusi.proto.linuxcnc.value

private val LOG = KotlinLogging.logger("ProgramsRootScreenModel")

class CncCommandRepositoryImpl : CncCommandRepository {
  private val commandWriter = CommandWriter()

  override fun setTaskMode(taskMode: TaskMode) {
    val mode = taskMode.mode
    if (mode == null) {
      LOG.error { "TaskMode unrecognized" }
      return
    }
    commandWriter.setTaskMode(mode)
  }

  override fun setTaskState(taskState: TaskState) {
    val stateNum = taskState.stateNum
    if (stateNum == null) {
      LOG.error { "TaskState unrecognized" }
      return
    }
    commandWriter.setTaskState(stateNum)
  }

  override fun taskAbort() {
    commandWriter.taskAbort()
  }

  override fun homeAll() {
    homeAxis(-1)
  }

  override fun homeAxis(jointNumber: Int) {
    commandWriter.homeAxis(jointNumber)
  }

  override fun overrideLimits(jointNumber: Int) {
    commandWriter.overrideLimits(jointNumber)
  }

  override fun unHomeAll() {
    unHomeAxis(-1)
  }

  override fun unHomeAxis(jointNumber: Int) {
    commandWriter.unHomeAxis(jointNumber)
  }

  override fun setFeedHold(hold: Boolean) {
    commandWriter.setFeedHold(if (hold) 1 else 0)
  }

  enum class AutoMode(val code: Int) {
    RUN(0),
    PAUSE(1),
    RESUME(2),
    STEP(3),
    REVERSE(4),
    FORWARD(5)
  }

  override fun runProgram() {
    setTaskMode(TaskMode.TaskModeAuto)
    commandWriter.setAuto(AutoMode.RUN.code)
  }

  override fun stopProgram() {
    setTaskMode(TaskMode.TaskModeManual)
    taskAbort()
  }

  override fun pauseProgram() {
    commandWriter.setAuto(AutoMode.PAUSE.code)
  }

  override fun resumeProgram() {
    commandWriter.setAuto(AutoMode.RESUME.code)
  }

  override fun stepProgram() {
    commandWriter.setAuto(AutoMode.STEP.code)
  }

  override fun setFeedOverride(double: Double) {
    commandWriter.setFeedOverride(double)
  }

  override fun setTeleopEnable(enabled: Boolean) {
    commandWriter.setMotionMode(if (enabled) 1 else 0)
  }

  override fun jogContinuous(jogMode: JogMode, axisOrJoint: Int, speed: Double) {
    val value = jogMode.value

    if (value == null) {
      LOG.error { "JogMode unrecognized" }
      return
    }
    commandWriter.jogContinuous(value, axisOrJoint, speed)
  }

  override fun jogIncremental(jogMode: JogMode, axisOrJoint: Int, stepSize: Double, speed: Double) {
    val value = jogMode.value

    if (value == null) {
      LOG.error { "JogMode unrecognized" }
      return
    }
    commandWriter.jogIncremental(value, axisOrJoint, stepSize, speed)
  }

  override fun jogAbsolute(jogMode: JogMode, axisOrJoint: Int, position: Double, speed: Double) {
    val value = jogMode.value

    if (value == null) {
      LOG.error { "JogMode unrecognized" }
      return
    }
    commandWriter.jogAbsolute(value, axisOrJoint, position, speed)
  }

  override fun jogStop(jogMode: JogMode, axisOrJoint: Int) {
    val value = jogMode.value

    if (value == null) {
      LOG.error { "JogMode unrecognized" }
      return
    }
    commandWriter.jogStop(value, axisOrJoint)
  }

  override fun setMinPositionLimit(jointNumber: Int, limit: Double) {
    commandWriter.setMinPositionLimit(jointNumber, limit)
  }

  override fun setMaxPositionLimit(jointNumber: Int, limit: Double) {
    commandWriter.setMaxPositionLimit(jointNumber, limit)
  }

  override fun setBacklash(jointNumber: Int, backlash: Double) {
    commandWriter.setBacklash(jointNumber, backlash)
  }

  override fun executeMdiCommand(command: String): Boolean {
    println("----MDI: $command")
    return commandWriter.sendMDICommand(command).also {
      if (!it) {
        println("-----MDI cmd failed: $command")
      }
    }
  }

  override fun loadProgramFile(filePath: String) {
    commandWriter.loadTaskPlan(filePath)
  }
}
