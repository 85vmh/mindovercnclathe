package com.mindovercnc.linuxcnc

import com.mindovercnc.repository.CncCommandRepository
import mu.KotlinLogging
import ro.dragossusi.proto.linuxcnc.*
import ro.dragossusi.proto.linuxcnc.status.JogMode
import ro.dragossusi.proto.linuxcnc.status.TaskMode
import ro.dragossusi.proto.linuxcnc.status.TaskState

private val LOG = KotlinLogging.logger("ProgramsRootScreenModel")

/** Implementation for [CncCommandRepository]. */
class CncCommandRepositoryImpl(private val linuxCncGrpc: LinuxCncGrpc.LinuxCncBlockingStub) :
  CncCommandRepository {

  @Deprecated("Will be replace by linuxCncGrpc", level = DeprecationLevel.WARNING)
  private val commandWriter = CommandWriter()

  override fun setTaskMode(taskMode: TaskMode) {
    val request = setTaskModeRequest { this.taskMode = taskMode }
    linuxCncGrpc.setTaskMode(request)
  }

  override fun setTaskState(taskState: TaskState) {
    val request = setTaskStateRequest { this.taskState = taskState }
    linuxCncGrpc.setTaskState(request)
  }

  override fun taskAbort() {
    val request =
      taskAbortRequest {
        // no-op
      }
    linuxCncGrpc.taskAbort(request)
  }

  override fun homeAll() {
    homeAxis(-1)
  }

  override fun homeAxis(jointNumber: Int) {
    val request = homeAxisRequest { this.jointNumber = jointNumber }
    linuxCncGrpc.homeAxis(request)
  }

  override fun overrideLimits(jointNumber: Int) {
    val request = overrideLimitsRequest { this.jointNumber = jointNumber }
    linuxCncGrpc.overrideLimits(request)
  }

  override fun unHomeAll() {
    unHomeAxis(-1)
  }

  override fun unHomeAxis(jointNumber: Int) {
    val request = unhomeAxisRequest { this.jointNumber = jointNumber }
    linuxCncGrpc.unhomeAxis(request)
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
    val request = jogContinuousRequest {
      this.jogMode = jogMode
      this.jointOrAxis = axisOrJoint
      this.velocity = speed
    }
    linuxCncGrpc.jogContinuous(request)
  }

  override fun jogIncremental(jogMode: JogMode, axisOrJoint: Int, stepSize: Double, speed: Double) {
    val request = jogIncrementalRequest {
      this.jogMode = jogMode
      this.jointOrAxis = axisOrJoint
      this.velocity = speed
      this.stepSize = stepSize
    }
    linuxCncGrpc.jogIncremental(request)
  }

  override fun jogAbsolute(jogMode: JogMode, axisOrJoint: Int, position: Double, speed: Double) {
    val request = jogAbsoluteRequest {
      this.jogMode = jogMode
      this.jointOrAxis = axisOrJoint
      this.velocity = speed
      this.position = position
    }
    linuxCncGrpc.jogAbsolute(request)
  }

  override fun jogStop(jogMode: JogMode, axisOrJoint: Int) {
    val request = jogStopRequest {
      this.jogMode = jogMode
      this.jointOrAxis = axisOrJoint
    }
    linuxCncGrpc.jogStop(request)
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
