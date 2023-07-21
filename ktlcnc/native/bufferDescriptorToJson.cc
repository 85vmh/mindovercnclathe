/*
 * file:        bufferDescriptorToJson.cc
 * purpose:     helper to dump member offset - used to check matching definitions
 * created:     190901
 */
#define __STDC_FORMAT_MACROS
#include <inttypes.h>
#include "config.h"
#include "emc.hh"
#include "emc_nml.hh"


#define O(x) (((long) &(emcStatus->x)) - ((long)emcStatus))


struct MemberDef {
  const char* name;
  long offset;
  const char* cmt;
  };

static MemberDef StatusMembers[] = {
// start like axis

// task
    {"task.mode",                   O(task.mode)},
    {"task.state",                  O(task.state)},
    {"task.execState",              O(task.execState)},
    {"task.interpreterState",       O(task.interpState)},
    {"task.callLevel",              O(task.callLevel), "current subroutine level - 0 if not in a subroutine, > 0 otherwise"},
    {"task.motionLine",             O(task.motionLine), "the line motion is executing"},
    {"task.currentLine",            O(task.currentLine), "the line currently executing"},
    {"task.readLine",               O(task.readLine), "The line interpreter has read"},
    {"task.isOptionalStop",         O(task.optional_stop_state), "If its on, we stop on M1"},
    {"task.isBlockDelete",          O(task.block_delete_state), "If its on, we ignore lines starting with '/' "},
    {"task.isDigitalInputTimeout",  O(task.input_timeout)},
    {"task.loadedFilePath",         O(task.file)},
    {"task.command",                O(task.command)},
    {"task.g5xOffsetXStart",        O(task.g5x_offset.tran.x)},
    {"task.g5xActiveIndex",         O(task.g5x_index)},
    {"task.g92OffsetXStart",        O(task.g92_offset.tran.x)},
    {"task.rotationXY",             O(task.rotation_xy)},
    {"task.toolOffsetXStart",       O(task.toolOffset.tran.x)},
    {"task.activeGCodes",           O(task.activeGCodes)},
    {"task.activeMCodes",           O(task.activeMCodes)},
    {"task.activeSettings",         O(task.activeSettings)},
    {"task.programUnits",           O(task.programUnits)},
    {"task.interpreterErrorCode",   O(task.interpreter_errcode)},
    {"task.taskPaused",             O(task.task_paused)},
    {"task.delayLeft",              O(task.delayLeft)},
    {"task.queuedMdiCommands",      O(task.queuedMDIcommands), "Number of MDI commands queued waiting to run." },

// motion trajectory
//   EMC_TRAJ_STAT traj
    {"motion.traj.linearUnits",             O(motion.traj.linearUnits), "Units per mm"},
    {"motion.traj.angularUnits",            O(motion.traj.angularUnits)},
    {"motion.traj.cycleTimeSec",            O(motion.traj.cycleTime)},
    {"motion.traj.jointsCount",             O(motion.traj.joints)},
    {"motion.traj.spindlesCount",           O(motion.traj.spindles)},
    {"motion.traj.axisMask",                O(motion.traj.axis_mask)},
    {"motion.traj.motionMode",              O(motion.traj.mode), "TRAJ_MODE_FREE | TRAJ_MODE_COORD | TRAJ_MODE_TELEOP" },
    {"motion.traj.isEnabled",               O(motion.traj.enabled)},
    {"motion.traj.isInPosition",            O(motion.traj.inpos)},
    {"motion.traj.pendingMotions",          O(motion.traj.queue)},
    {"motion.traj.activeMotions",           O(motion.traj.activeQueue)},
    {"motion.traj.isMotionQueueFull",       O(motion.traj.queueFull)},
    {"motion.traj.currentMotionId",         O(motion.traj.id)},
    {"motion.traj.isMotionPaused",          O(motion.traj.paused)},
    {"motion.traj.velocityScaleFactor",     O(motion.traj.scale)},
    {"motion.traj.rapidScaleFactor",        O(motion.traj.rapid_scale)},

    {"motion.traj.cmdPosXStart",            O(motion.traj.position.tran.x)},
    {"motion.traj.actualPosXStart",         O(motion.traj.actualPosition.tran.x)},
    {"motion.traj.systemVelocity",          O(motion.traj.velocity)},
    {"motion.traj.systemAcceleration",      O(motion.traj.acceleration)},
    {"motion.traj.maxVelocity",             O(motion.traj.maxVelocity)},
    {"motion.traj.maxAcceleration",         O(motion.traj.maxAcceleration)},

    {"motion.traj.probedPosXStart",         O(motion.traj.probedPosition.tran.x)},
    {"motion.traj.isProbeTripped",          O(motion.traj.probe_tripped)},
    {"motion.traj.isProbing",               O(motion.traj.probing)},
    {"motion.traj.probeInputValue",         O(motion.traj.probeval)},
    {"motion.traj.kinematicsType",          O(motion.traj.kinematics_type), "identity=1,serial=2,parallel=3,custom=4"},
    {"motion.traj.motionType",              O(motion.traj.motion_type)
      , "MOTION_TYPE_TRAVERSE, MOTION_TYPE_FEED, MOTION_TYPE_ARC, MOTION_TYPE_TOOLCHANGE,\n"
        "MOTION_TYPE_PROBING, or MOTION_TYPE_INDEXROTARY), or 0 if no motion is currently taking place."},
    {"motion.traj.currentMoveDtg",          O(motion.traj.distance_to_go)},
    {"motion.traj.dtgXStart",               O(motion.traj.dtg.tran.x)},
    {"motion.traj.currentMoveVelocity",     O(motion.traj.current_vel)},
    {"motion.traj.isFeedOverrideEnabled",   O(motion.traj.feed_override_enabled)},
    {"motion.traj.isAdaptiveFeedEnabled",   O(motion.traj.adaptive_feed_enabled)},
    {"motion.traj.isFeedHoldEnabled",       O(motion.traj.feed_hold_enabled)},

// EMC_JOINT_STAT joint

    {"motion.joint0",                        O(motion.joint[0])},
    {"motion.joint1",                        O(motion.joint[1])},

    {"motion.joint0.jointType",              O(motion.joint[0].jointType)},
    {"motion.joint0.units",                  O(motion.joint[0].units)},
    {"motion.joint0.backlash",               O(motion.joint[0].backlash)},
    {"motion.joint0.minPositionLimit",       O(motion.joint[0].minPositionLimit)},
    {"motion.joint0.maxPositionLimit",       O(motion.joint[0].maxPositionLimit)},
    {"motion.joint0.maxFollowingError",      O(motion.joint[0].maxFerror)},
    {"motion.joint0.minFollowingError",      O(motion.joint[0].minFerror)},
    {"motion.joint0.followingErrorCurrent",  O(motion.joint[0].ferrorCurrent)},
    {"motion.joint0.followingErrorHighMark", O(motion.joint[0].ferrorHighMark)},
    {"motion.joint0.output",                 O(motion.joint[0].output)},
    {"motion.joint0.input",                  O(motion.joint[0].input)},
    {"motion.joint0.velocity",               O(motion.joint[0].velocity)},
    {"motion.joint0.inPosition",             O(motion.joint[0].inpos)},
    {"motion.joint0.homing",                 O(motion.joint[0].homing)},
    {"motion.joint0.homed",                  O(motion.joint[0].homed)},
    {"motion.joint0.fault",                  O(motion.joint[0].fault)},
    {"motion.joint0.enabled",                O(motion.joint[0].enabled)},
    {"motion.joint0.minSoftLimit",           O(motion.joint[0].minSoftLimit)},
    {"motion.joint0.maxSoftLimit",           O(motion.joint[0].maxSoftLimit)},
    {"motion.joint0.minHardLimit",           O(motion.joint[0].minHardLimit)},
    {"motion.joint0.maxHardLimit",           O(motion.joint[0].maxHardLimit)},
    {"motion.joint0.overrideLimits",         O(motion.joint[0].overrideLimits)},

// EMC_AXIS_STAT axis

    {"motion.axis0",                        O(motion.axis[0])},
    {"motion.axis1",                        O(motion.axis[1])},

    {"motion.axis0.minPositionLimit",       O(motion.axis[0].minPositionLimit)},
    {"motion.axis0.maxPositionLimit",       O(motion.axis[0].maxPositionLimit)},
    {"motion.axis0.velocity",               O(motion.axis[0].velocity)},


// EMC_SPINDLE_STAT motion.spindle
    {"motion.spindle0",                    O(motion.spindle[0])},
    {"motion.spindle1",                    O(motion.spindle[1])},

    {"motion.spindle0.speed",               O(motion.spindle[0].speed)},
    {"motion.spindle0.scale",               O(motion.spindle[0].spindle_scale)},
    {"motion.spindle0.cssMaximum",          O(motion.spindle[0].css_maximum)},
    {"motion.spindle0.cssFactor",           O(motion.spindle[0].css_factor)},
    {"motion.spindle0.state",               O(motion.spindle[0].state)},
    {"motion.spindle0.direction",           O(motion.spindle[0].direction)},
    {"motion.spindle0.brake",               O(motion.spindle[0].brake)},
    {"motion.spindle0.increasing",          O(motion.spindle[0].increasing)},
    {"motion.spindle0.enabled",             O(motion.spindle[0].enabled)},
    {"motion.spindle0.orientState",         O(motion.spindle[0].orient_state)},
    {"motion.spindle0.orientFault",         O(motion.spindle[0].orient_fault)},
    {"motion.spindle0.overrideEnabled",     O(motion.spindle[0].spindle_override_enabled)},
    {"motion.spindle0.homed",               O(motion.spindle[0].homed)},


    {"motion.digitalInputsInt",             O(motion.synch_di)},
    {"motion.digitalOutputsInt",            O(motion.synch_do)},
    {"motion.analogInputsDouble",           O(motion.analog_input)},
    {"motion.analogOutputsDouble",          O(motion.analog_output)},
    {"motion.debug",                        O(motion.debug)},
    {"motion.onSoftLimit",                  O(motion.on_soft_limit)},
    {"motion.externalOffsetsApplied",       O(motion.external_offsets_applied)},
    {"motion.externalOffsetPosXStart",      O(motion.eoffset_pose.tran.x)},
    {"motion.numExtraJoints",               O(motion.numExtraJoints)},


// EMC_TOOL_STAT io.tool
    {"io.cycleTime",                    O(io.cycleTime)},
    {"io.debug",                        O(io.debug)},
    {"io.reason",                       O(io.reason)},
    {"io.faultDuringM6",                O(io.fault)},

    {"io.tool.pocketPrepared",          O(io.tool.pocketPrepped)},
    {"io.tool.toolInSpindle",           O(io.tool.toolInSpindle)},
#ifdef TOOL_NML
    {"io.tool.toolTable",               O(io.tool.toolTable)},
#endif

// EMC_COOLANT_STAT io.coolant
    {"io.coolant.mist",                 O(io.coolant.mist)},
    {"io.coolant.flood",                O(io.coolant.flood)},

// EMC_AUX_STAT     io.aux
    {"io.aux.estop",                    O(io.aux.estop)},

// EMC_LUBE_STAT    io.lube
    {"io.aux.lubeOn",                   O(io.lube.on)},
    {"io.aux.lubeLevelOk",              O(io.lube.level)},

    {NULL}
};

/*
 *
 */
int main(int cArgs, char** pArgs) {
    char buffer[4096]; // Adjust the buffer size as needed
    int offset = 0;

    FILE* file = fopen("bufferDescriptor.json", "w"); // Open the file in write mode

    offset += sprintf(buffer + offset, "{");
    for (const MemberDef* p = StatusMembers; p->offset > 0; ++p) {
        offset += sprintf(buffer + offset, "\"%s\" : %d", p->name, p->offset);

        // Check if it's not the last item, then add a comma
        if ((p + 1)->offset > 0) {
            offset += sprintf(buffer + offset, ",");
        }
    }
    offset += sprintf(buffer + offset, "}");

    // Write the buffer to the file
    fprintf(file, "%s", buffer);

    // Close the file
    fclose(file);

    return 0;
}

