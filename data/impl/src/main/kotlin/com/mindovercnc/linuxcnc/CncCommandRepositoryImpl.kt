package com.mindovercnc.linuxcnc

import com.mindovercnc.repository.CncCommandRepository
import mu.KotlinLogging
import ro.dragossusi.proto.linuxcnc.*
import ro.dragossusi.proto.linuxcnc.status.JogMode
import ro.dragossusi.proto.linuxcnc.status.TaskMode
import ro.dragossusi.proto.linuxcnc.status.TaskState
import ro.dragossusi.proto.linuxcnc.status.auto.AutoMode
import ro.dragossusi.proto.linuxcnc.status.motion.MotionMode

/** Implementation for [CncCommandRepository]. */
class CncCommandRepositoryImpl(private val linuxCncGrpc: LinuxCncGrpc.LinuxCncBlockingStub) :
  CncCommandRepository {

  private val logger = KotlinLogging.logger("ProgramsRootScreenModel")

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
    val request = setFeedHoldRequest { mode = if (hold) 1 else 0 }
    linuxCncGrpc.setFeedHold(request)
  }

  override fun runProgram() {
    setTaskMode(TaskMode.TaskModeAuto)
    val request = setAutoRequest {
      autoMode = AutoMode.RUN
      fromLine = 0
    }
    linuxCncGrpc.setAuto(request)
  }

  override fun stopProgram() {
    setTaskMode(TaskMode.TaskModeManual)
    taskAbort()
  }

  override fun pauseProgram() {
    val request = setAutoRequest {
      autoMode = AutoMode.PAUSE
      fromLine = 0
    }
    linuxCncGrpc.setAuto(request)
  }

  override fun resumeProgram() {
    val request = setAutoRequest {
      autoMode = AutoMode.RESUME
      fromLine = 0
    }
    linuxCncGrpc.setAuto(request)
  }

  override fun stepProgram() {
    val request = setAutoRequest {
      autoMode = AutoMode.STEP
      fromLine = 0
    }
    linuxCncGrpc.setAuto(request)
  }

  override fun setFeedOverride(double: Double) {
    val request = setFeedOverrideRequest { rate = double }
    linuxCncGrpc.setFeedOverride(request)
  }

  override fun setTeleopEnable(enabled: Boolean) {
    val request = setMotionModeRequest {
      motionMode = if (enabled) MotionMode.TELEOP else MotionMode.JOINT
    }
    linuxCncGrpc.setMotionMode(request)
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
    val request = setMinPositionPositionLimitRequest {
      jointNum = jointNumber
      this.limit = limit
    }
    linuxCncGrpc.setMinPositionLimit(request)
  }

  override fun setMaxPositionLimit(jointNumber: Int, limit: Double) {
    val request = setMaxPositionPositionLimitRequest {
      jointNum = jointNumber
      this.limit = limit
    }
    linuxCncGrpc.setMaxPositionLimit(request)
  }

  override fun setBacklash(jointNumber: Int, backlash: Double) {
    val request = setBacklashRequest {
      jointNum = jointNumber
      this.backlash = backlash
    }
    linuxCncGrpc.setBacklash(request)
  }

  override fun executeMdiCommand(command: String): Boolean {
    println("----MDI: $command")
    val request = sendMdiCommandRequest { this.command = command }
    val result = linuxCncGrpc.sendMdiCommand(request).result > 0

    if (!result) {
      println("-----MDI cmd failed: $command")
    }

    return result
  }

  override fun loadProgramFile(filePath: String) {
    val request = loadTaskPlanRequest { fileName = filePath }
    linuxCncGrpc.loadTaskPlan(request)
  }
}
