package com.mindovercnc.data.linuxcnc.remote

import com.mindovercnc.data.linuxcnc.CncCommandRepository
import mu.KotlinLogging
import ro.dragossusi.proto.linuxcnc.*
import ro.dragossusi.proto.linuxcnc.status.JogMode
import ro.dragossusi.proto.linuxcnc.status.TaskMode
import ro.dragossusi.proto.linuxcnc.status.TaskState
import ro.dragossusi.proto.linuxcnc.status.auto.AutoMode
import ro.dragossusi.proto.linuxcnc.status.motion.MotionMode

/** Implementation for [CncCommandRepository]. */
class CncCommandRepositoryRemote(
    private val linuxCncGrpc: LinuxCncClient
) : CncCommandRepository {

    override fun setTaskMode(taskMode: TaskMode) {
        val request = SetTaskModeRequest(task_mode = taskMode)
        linuxCncGrpc.SetTaskMode().executeBlocking(request)
    }

    override fun setTaskState(taskState: TaskState) {
        val request = SetTaskStateRequest(task_state = taskState)
        linuxCncGrpc.SetTaskState().executeBlocking(request)
    }

    override fun taskAbort() {
        val request =
            TaskAbortRequest(
                // no-op
            )
        linuxCncGrpc.TaskAbort().executeBlocking(request)
    }

    override fun homeAll() {
        homeAxis(-1)
    }

    override fun homeAxis(jointNumber: Int) {
        val request = HomeAxisRequest(joint_number = jointNumber)
        linuxCncGrpc.HomeAxis().executeBlocking(request)
    }

    override fun overrideLimits(jointNumber: Int) {
        val request = OverrideLimitsRequest(joint_number = jointNumber)
        linuxCncGrpc.OverrideLimits().executeBlocking(request)
    }

    override fun unHomeAll() {
        unHomeAxis(-1)
    }

    override fun unHomeAxis(jointNumber: Int) {
        val request = UnhomeAxisRequest(joint_number = jointNumber)
        linuxCncGrpc.UnhomeAxis().executeBlocking(request)
    }

    override fun setFeedHold(hold: Boolean) {
        val request = SetFeedHoldRequest(mode = if (hold) 1 else 0)
        linuxCncGrpc.SetFeedHold().executeBlocking(request)
    }

    override fun runProgram() {
        setTaskMode(TaskMode.TaskModeAuto)
        val request = SetAutoRequest(
            auto_mode = AutoMode.RUN,
            from_line = 0
        )
        linuxCncGrpc.SetAuto().executeBlocking(request)
    }

    override fun stopProgram() {
        setTaskMode(TaskMode.TaskModeManual)
        taskAbort()
    }

    override fun pauseProgram() {
        val request = SetAutoRequest(
            auto_mode = AutoMode.PAUSE,
            from_line = 0
        )
        linuxCncGrpc.SetAuto().executeBlocking(request)
    }

    override fun resumeProgram() {
        val request = SetAutoRequest(
            auto_mode = AutoMode.RESUME,
            from_line = 0
        )
        linuxCncGrpc.SetAuto().executeBlocking(request)
    }

    override fun stepProgram() {
        val request = SetAutoRequest(
            auto_mode = AutoMode.STEP,
            from_line = 0
        )
        linuxCncGrpc.SetAuto().executeBlocking(request)
    }

    override fun setFeedOverride(double: Double) {
        val request = SetFeedOverrideRequest(rate = double)
        linuxCncGrpc.SetFeedOverride().executeBlocking(request)
    }

    override fun setTeleopEnable(enabled: Boolean) {
        val request = SetMotionModeRequest(
            motion_mode = if (enabled) MotionMode.TELEOP else MotionMode.JOINT
        )
        linuxCncGrpc.SetMotionMode().executeBlocking(request)
    }

    override fun jogContinuous(jogMode: JogMode, axisOrJoint: Int, speed: Double) {
        val request = JogContinuousRequest(
            jog_mode = jogMode,
            joint_or_axis = axisOrJoint,
            velocity = speed
        )
        linuxCncGrpc.JogContinuous().executeBlocking(request)
    }

    override fun jogIncremental(jogMode: JogMode, axisOrJoint: Int, stepSize: Double, speed: Double) {
        val request = JogIncrementalRequest(
            jog_mode = jogMode,
            joint_or_axis = axisOrJoint,
            velocity = speed,
            stepSize = stepSize,
        )
        linuxCncGrpc.JogIncremental().executeBlocking(request)
    }

    override fun jogAbsolute(jogMode: JogMode, axisOrJoint: Int, position: Double, speed: Double) {
        val request = JogAbsoluteRequest(
            jog_mode = jogMode,
            joint_or_axis = axisOrJoint,
            velocity = speed,
            position = position,
        )
        linuxCncGrpc.JogAbsolute().executeBlocking(request)
    }

    override fun jogStop(jogMode: JogMode, axisOrJoint: Int) {
        val request = JogStopRequest(
            jog_mode = jogMode,
            joint_or_axis = axisOrJoint,
        )
        linuxCncGrpc.JogStop().executeBlocking(request)
    }

    override fun setMinPositionLimit(jointNumber: Int, limit: Double) {
        val request = SetMinPositionPositionLimitRequest(
            joint_num = jointNumber,
            limit = limit,
        )
        linuxCncGrpc.SetMinPositionLimit().executeBlocking(request)
    }

    override fun setMaxPositionLimit(jointNumber: Int, limit: Double) {
        val request = SetMaxPositionPositionLimitRequest(
            joint_num = jointNumber,
            limit = limit,
        )
        linuxCncGrpc.SetMaxPositionLimit().executeBlocking(request)
    }

    override fun setBacklash(jointNumber: Int, backlash: Double) {
        val request = SetBacklashRequest(
            joint_num = jointNumber,
            backlash = backlash,
        )
        linuxCncGrpc.SetBacklash().executeBlocking(request)
    }

    override fun executeMdiCommand(command: String): Boolean {
        logger.info { "----MDI: $command" }
        val request = SendMdiCommandRequest(command = command)
        val result = linuxCncGrpc.SendMdiCommand().executeBlocking(request).result > 0

        if (!result) {
            logger.info { "-----MDI cmd failed: $command" }
        }

        return result
    }

    override fun loadProgramFile(filePath: String) {
        val request = LoadTaskPlanRequest(file_name = filePath)
        linuxCncGrpc.LoadTaskPlan().executeBlocking(request)
    }

    companion object {
        private val logger = KotlinLogging.logger("ProgramsRootScreenModel")
    }
}