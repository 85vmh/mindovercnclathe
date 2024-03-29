package com.mindovercnc.linuxcnc.nml

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The serialName must match the name of the key in the linuxcnc shared memory buffer.
 */

@Serializable
data class MemoryEntries(
    @SerialName("task.mode") val taskMode: Int,
    @SerialName("task.state") val taskState: Int,
    @SerialName("task.execState") val execState: Int,
    @SerialName("task.interpreterState") val interpreterState: Int,
    @SerialName("task.callLevel") val subroutineCallLevel: Int,
    @SerialName("task.motionLine") val motionLine: Int,
    @SerialName("task.currentLine") val currentLine: Int,
    @SerialName("task.readLine") val readLine: Int,
    @SerialName("task.isOptionalStop") val isOptionalStop: Int,
    @SerialName("task.isBlockDelete") val isBlockDelete: Int,
    @SerialName("task.isDigitalInputTimeout") val isDigitalInTimeout: Int,
    @SerialName("task.loadedFilePath") val loadedFilePath: Int,
    @SerialName("task.command") val command: Int,
    @SerialName("task.g5xOffsetXStart") val g5xOffsetXStart: Int,
    @SerialName("task.g5xActiveIndex") val g5xActiveIndex: Int,
    @SerialName("task.g92OffsetXStart") val g92OffsetXStart: Int,
    @SerialName("task.rotationXY") val rotationXY: Int,
    @SerialName("task.toolOffsetXStart") val toolOffsetXStart: Int,
    @SerialName("task.activeGCodes") val activeGCodes: Int,
    @SerialName("task.activeMCodes") val activeMCodes: Int,
    @SerialName("task.activeSettings") val activeSettings: Int,
    @SerialName("task.programUnits") val programUnits: Int,
    @SerialName("task.interpreterErrorCode") val interpreterErrorCode: Int,
    @SerialName("task.taskPaused") val taskPaused: Int,
    @SerialName("task.delayLeft") val delayLeft: Int,
    @SerialName("task.queuedMdiCommands") val queuedMdiCommands: Int,
    @SerialName("motion.traj.linearUnits") val linearUnits: Int,
    @SerialName("motion.traj.angularUnits") val angularUnits: Int,
    @SerialName("motion.traj.cycleTimeSec") val cycleTimeSeconds: Int,
    @SerialName("motion.traj.jointsCount") val jointsCount: Int,
    @SerialName("motion.traj.spindlesCount") val spindlesCount: Int,
    @SerialName("motion.traj.axisMask") val axisMask: Int,
    @SerialName("motion.traj.motionMode") val motionMode: Int,
    @SerialName("motion.traj.isEnabled") val isEnabled: Int,
    @SerialName("motion.traj.isInPosition") val isInPosition: Int,
    @SerialName("motion.traj.pendingMotions") val pendingMotions: Int,
    @SerialName("motion.traj.activeMotions") val activeMotions: Int,
    @SerialName("motion.traj.isMotionQueueFull") val isMotionQueueFull: Int,
    @SerialName("motion.traj.currentMotionId") val currentMotionId: Int,
    @SerialName("motion.traj.isMotionPaused") val isMotionPaused: Int,
    @SerialName("motion.traj.velocityScaleFactor") val velocityScaleFactor: Int,
    @SerialName("motion.traj.rapidScaleFactor") val rapidScaleFactor: Int,
    @SerialName("motion.traj.cmdPosXStart") val commandedPositionXStart: Int,
    @SerialName("motion.traj.actualPosXStart") val actualPositionXStart: Int,
    @SerialName("motion.traj.systemVelocity") val systemVelocity: Int,
    @SerialName("motion.traj.systemAcceleration") val systemAcceleration: Int,
    @SerialName("motion.traj.maxVelocity") val maxVelocity: Int,
    @SerialName("motion.traj.maxAcceleration") val maxAcceleration: Int,
    @SerialName("motion.traj.probedPosXStart") val probedPositionXStart: Int,
    @SerialName("motion.traj.isProbeTripped") val isProbeTripped: Int,
    @SerialName("motion.traj.isProbing") val isProbing: Int,
    @SerialName("motion.traj.probeInputValue") val probeInputValue: Int,
    @SerialName("motion.traj.kinematicsType") val kinematicsType: Int,
    @SerialName("motion.traj.motionType") val motionType: Int,
    @SerialName("motion.traj.currentMoveDtg") val currentMoveDtg: Int,
    @SerialName("motion.traj.dtgXStart") val dtgPositionXStart: Int,
    @SerialName("motion.traj.currentMoveVelocity") val currentMoveVelocity: Int,
    @SerialName("motion.traj.isFeedOverrideEnabled") val isFeedOverrideEnabled: Int,
    @SerialName("motion.traj.isAdaptiveFeedEnabled") val isAdaptiveFeedEnabled: Int,
    @SerialName("motion.traj.isFeedHoldEnabled") val isFeedHoldEnabled: Int,
    @SerialName("motion.joint0") val joint0: Int,
    @SerialName("motion.joint1") val joint1: Int,
    @SerialName("motion.joint0.jointType") val joint0Type: Int,
    @SerialName("motion.joint0.units") val joint0Units: Int,
    @SerialName("motion.joint0.backlash") val joint0Backlash: Int,
    @SerialName("motion.joint0.minPositionLimit") val joint0MinPositionLimit: Int,
    @SerialName("motion.joint0.maxPositionLimit") val joint0MaxPositionLimit: Int,
    @SerialName("motion.joint0.maxFollowingError") val joint0MaxFollowingError: Int,
    @SerialName("motion.joint0.minFollowingError") val joint0MinFollowingError: Int,
    @SerialName("motion.joint0.followingErrorCurrent") val joint0FollowingErrorCurrent: Int,
    @SerialName("motion.joint0.followingErrorHighMark") val joint0FollowingErrorHighMark: Int,
    @SerialName("motion.joint0.output") val joint0CommandedOutputPosition: Int,
    @SerialName("motion.joint0.input") val joint0CurrentInputPosition: Int,
    @SerialName("motion.joint0.velocity") val joint0CurrentVelocity: Int,
    @SerialName("motion.joint0.inPosition") val joint0IsInPosition: Int,
    @SerialName("motion.joint0.homing") val joint0IsHoming: Int,
    @SerialName("motion.joint0.homed") val joint0IsHomed: Int,
    @SerialName("motion.joint0.fault") val joint0IsFaulted: Int,
    @SerialName("motion.joint0.enabled") val joint0IsEnabled: Int,
    @SerialName("motion.joint0.minSoftLimit") val joint0IsMinSoftLimitReached: Int,
    @SerialName("motion.joint0.maxSoftLimit") val joint0IsMaxSoftLimitReached: Int,
    @SerialName("motion.joint0.minHardLimit") val joint0IsMinHardLimitReached: Int,
    @SerialName("motion.joint0.maxHardLimit") val joint0IsMaxHardLimitReached: Int,
    @SerialName("motion.joint0.overrideLimits") val joint0IsLimitOverrideOn: Int,
    @SerialName("motion.axis0") val axis0: Int,
    @SerialName("motion.axis1") val axis1: Int,
    @SerialName("motion.axis0.minPositionLimit") val axis0MinPositionLimit: Int,
    @SerialName("motion.axis0.maxPositionLimit") val axis0MaxPositionLimit: Int,
    @SerialName("motion.axis0.velocity") val axis0Velocity: Int,
    @SerialName("motion.spindle0") val spindle0: Int,
    @SerialName("motion.spindle1") val spindle1: Int,
    @SerialName("motion.spindle0.speed") val spindle0Speed: Int,
    @SerialName("motion.spindle0.scale") val spindle0Scale: Int,
    @SerialName("motion.spindle0.cssMaximum") val spindle0CssMaximum: Int,
    @SerialName("motion.spindle0.cssFactor") val spindle0CssFactor: Int,
    @SerialName("motion.spindle0.state") val spindle0State: Int,
    @SerialName("motion.spindle0.direction") val spindle0Direction: Int,
    @SerialName("motion.spindle0.brake") val spindle0Brake: Int,
    @SerialName("motion.spindle0.increasing") val spindle0Increasing: Int,
    @SerialName("motion.spindle0.enabled") val spindle0Enabled: Int,
    @SerialName("motion.spindle0.orientState") val spindle0OrientState: Int,
    @SerialName("motion.spindle0.orientFault") val spindle0OrientFault: Int,
    @SerialName("motion.spindle0.overrideEnabled") val spindle0OverrideEnabled: Int,
    @SerialName("motion.spindle0.homed") val spindle0Homed: Int,
    @SerialName("motion.digitalInputsInt") val motion64DigitalInputsInt: Int,
    @SerialName("motion.digitalOutputsInt") val motion64DigitalOutputsInt: Int,
    @SerialName("motion.analogInputsDouble") val motion64AnalogInputsDouble: Int,
    @SerialName("motion.analogOutputsDouble") val motion64AnalogOutputsDouble: Int,
    @SerialName("motion.debug") val motionDebug: Int,
    @SerialName("motion.onSoftLimit") val motionOnSoftLimit: Int,
    @SerialName("motion.externalOffsetsApplied") val externalOffsetsApplied: Int,
    @SerialName("motion.externalOffsetPosXStart") val externalOffsetsPositionXStart: Int,
    @SerialName("motion.numExtraJoints") val numExtraJoints: Int,
    @SerialName("io.cycleTime") val ioCycleTime: Int,
    @SerialName("io.debug") val ioDebug: Int,
    @SerialName("io.reason") val ioReason: Int,
    @SerialName("io.faultDuringM6") val ioFaultDuringM6: Int,
    @SerialName("io.tool.pocketPrepared") val ioPocketPrepared: Int,
    @SerialName("io.tool.toolInSpindle") val ioLoadedTool: Int,
    @SerialName("io.coolant.mist") val ioCoolantMist: Int,
    @SerialName("io.coolant.flood") val ioCoolantFlood: Int,
    @SerialName("io.aux.estop") val ioAuxEstop: Int,
    @SerialName("io.aux.lubeOn") val ioAuxLubeOn: Int,
    @SerialName("io.aux.lubeLevelOk") val ioAuxLubeLevelOk: Int
)
