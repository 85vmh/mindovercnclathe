/*
 * **************************************************************************
 *
 *  file:       com_mindovercnc_linuxcnc_CommandWriter.cc
 *  project:    GUI for linuxcnc
 *  subproject: C-stub to access nml-structure in shared memory
 *  purpose:    send commands to linuxcnc backend
 *  created:    18.4.2020 by Django Reinhard
 *              followed code from linuxcnc
 *              rewrite to minimize dependencies 10.7.2021
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
#include <stat_msg.hh>
#include <cmd_msg.hh>
#include <emc_nml.hh>
#include <stdio.h>
#include <cstring>
#include <sys/time.h>
#include <com_mindovercnc_linuxcnc_CommandWriter.h>
#include <config.h>

static RCS_CMD_CHANNEL *cCmd;
static RCS_STAT_CHANNEL *cStat;
static EMC_STAT *status;

#define EMC_COMMAND_TIMEOUT 5.0 // how long to wait until timeout
#define EMC_COMMAND_DELAY 0.01  // seconds to sleep between checks

static void sleep(double s)
{
  struct timeval tv;

  tv.tv_sec = s;
  tv.tv_usec = s * 1000000;
  select(0, NULL, NULL, NULL, &tv);
}

static int sendCommand(RCS_CMD_MSG &msg)
{
  if (cCmd->write(&msg))
    return -1;

  for (double end = 0.0; end < EMC_COMMAND_TIMEOUT; end += EMC_COMMAND_DELAY)
  {
    cStat->peek();

    if ((status->echo_serial_number - msg.serial_number) >= 0)
      return 0;
    sleep(EMC_COMMAND_DELAY);
  }
  return -1;
}

/*
 * Class:     com_mindovercnc_linuxcnc_CommandWriter
 * Method:    init
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_mindovercnc_linuxcnc_CommandWriter_init(JNIEnv *env, jobject thisObject)
{
  cCmd = new RCS_CMD_CHANNEL(emcFormat, "emcCommand", "xemc", EMC2_DEFAULT_NMLFILE);
  if (!cCmd || !cCmd->valid())
  {
    delete cCmd;
    cCmd = NULL;

    return -1;
  }
  cStat = new RCS_STAT_CHANNEL(emcFormat, "emcStatus", "xemc", EMC2_DEFAULT_NMLFILE);
  if (!cStat || !cStat->valid())
  {
    delete cCmd;
    delete cStat;
    cCmd = NULL;
    cStat = NULL;

    return -2;
  }
  else
  {
    status = static_cast<EMC_STAT *>(cStat->get_address());
  }
  return 0;
}

JNIEXPORT void JNICALL Java_com_mindovercnc_linuxcnc_CommandWriter_setMotionMode(JNIEnv *env, jobject thisObject, jint motionMode)
{
  EMC_TRAJ_SET_TELEOP_ENABLE teleop;
  teleop.enable = motionMode;
  sendCommand(teleop);
}

JNIEXPORT void JNICALL Java_com_mindovercnc_linuxcnc_CommandWriter_jogContinuous(JNIEnv *env, jobject thisObject, jint jogMode, jint axisOrJoint, jdouble speed)
{
  EMC_JOG_CONT jc;

  jc.jjogmode = jogMode;
  jc.joint_or_axis = axisOrJoint;
  jc.vel = speed;
  sendCommand(jc);
}

JNIEXPORT void JNICALL Java_com_mindovercnc_linuxcnc_CommandWriter_jogIncremental(JNIEnv *env, jobject thisObject, jint jogMode, jint axisOrJoint, jdouble stepSize, jdouble speed)
{
  EMC_JOG_INCR ji;

  ji.jjogmode = jogMode;
  ji.joint_or_axis = axisOrJoint;
  ji.incr = stepSize;
  ji.vel = speed;
  sendCommand(ji);
}

JNIEXPORT void JNICALL Java_com_mindovercnc_linuxcnc_CommandWriter_jogAbsolute(JNIEnv *env, jobject thisObject, jint jogMode, jint axisOrJoint, jdouble position, jdouble speed)
{
  EMC_JOG_ABS ja;

  ja.jjogmode = jogMode;
  ja.joint_or_axis = axisOrJoint;
  ja.pos = position;
  ja.vel = speed;
  sendCommand(ja);
}

JNIEXPORT void JNICALL Java_com_mindovercnc_linuxcnc_CommandWriter_jogStop(JNIEnv *env, jobject thisObject, jint jogMode, jint axisOrJoint)
{
  EMC_JOG_STOP js;

  js.jjogmode = jogMode;
  js.joint_or_axis = axisOrJoint;
  sendCommand(js);
}

JNIEXPORT void JNICALL Java_com_mindovercnc_linuxcnc_CommandWriter_homeAxis(JNIEnv *env, jobject thisObject, jint jointNum)
{
  EMC_JOINT_HOME jh;

  jh.joint = jointNum;
  sendCommand(jh);
}

JNIEXPORT void JNICALL Java_com_mindovercnc_linuxcnc_CommandWriter_unHomeAxis(JNIEnv *env, jobject thisObject, jint jointNum)
{
  EMC_JOINT_UNHOME juh;

  juh.joint = jointNum;
  sendCommand(juh);
}

JNIEXPORT void JNICALL Java_com_mindovercnc_linuxcnc_CommandWriter_overrideLimits(JNIEnv *env, jobject thisObject, jint jointNum)
{
  EMC_JOINT_OVERRIDE_LIMITS ovl;

  ovl.joint = jointNum;
  sendCommand(ovl);
}

JNIEXPORT void JNICALL Java_com_mindovercnc_linuxcnc_CommandWriter_setBacklash(JNIEnv *env, jobject thisObject, jint jointNum, jdouble backlash)
{
  EMC_JOINT_SET_BACKLASH bkl;

  bkl.joint = jointNum;
  bkl.backlash = backlash;
  sendCommand(bkl);
}

JNIEXPORT void JNICALL Java_com_mindovercnc_linuxcnc_CommandWriter_setMinPositionLimit(JNIEnv *env, jobject thisObject, jint jointNum, jdouble limit)
{
  EMC_JOINT_SET_MIN_POSITION_LIMIT minPosLim;

  minPosLim.joint = jointNum;
  minPosLim.limit = limit;
  sendCommand(minPosLim);
}

JNIEXPORT void JNICALL Java_com_mindovercnc_linuxcnc_CommandWriter_setMaxPositionLimit(JNIEnv *env, jobject thisObject, jint jointNum, jdouble limit)
{
  EMC_JOINT_SET_MAX_POSITION_LIMIT maxPosLim;

  maxPosLim.joint = jointNum;
  maxPosLim.limit = limit;
  sendCommand(maxPosLim);
}

JNIEXPORT void JNICALL Java_com_mindovercnc_linuxcnc_CommandWriter_loadTaskPlan(JNIEnv *env, jobject thisObject, jstring gcodeFile)
{
  EMC_TASK_PLAN_CLOSE pc;
  EMC_TASK_PLAN_OPEN po;
  const char *fileName = env->GetStringUTFChars(gcodeFile, NULL);

  sendCommand(pc);
  strcpy(po.file, fileName);
  sendCommand(po);
}

/*
 * Class:     com_mindovercnc_linuxcnc_CommandWriter
 * Method:    loadToolTable
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_mindovercnc_linuxcnc_CommandWriter_loadToolTable(JNIEnv *env, jobject thisObject, jstring toolTableFile)
{

  EMC_TOOL_LOAD_TOOL_TABLE ltt;
  const char *fileName = NULL;

  if (toolTableFile != NULL)
  {
    fileName = env->GetStringUTFChars(toolTableFile, NULL);
    strcpy(ltt.file, fileName);
  }
  sendCommand(ltt);
}

/*
 * Class:     com_mindovercnc_linuxcnc_CommandWriter
 * Method:    sendMDICommand
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT jboolean JNICALL Java_com_mindovercnc_linuxcnc_CommandWriter_sendMDICommand(JNIEnv *env, jobject thisObject, jstring command)
{
  EMC_TASK_PLAN_EXECUTE pe;
  const char *cmd = env->GetStringUTFChars(command, NULL);

  strcpy(pe.command, cmd);
  return sendCommand(pe) == 0; // success
}

#define LOCAL_AUTO_RUN (0)
#define LOCAL_AUTO_PAUSE (1)
#define LOCAL_AUTO_RESUME (2)
#define LOCAL_AUTO_STEP (3)
#define LOCAL_AUTO_REVERSE (4)
#define LOCAL_AUTO_FORWARD (5)

/*
 * Class:     com_mindovercnc_linuxcnc_CommandWriter
 * Method:    setAuto
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_mindovercnc_linuxcnc_CommandWriter_setAuto(JNIEnv *env, jobject thisObject, jint autoMode, jint line)
{
  int rv = 0;

  switch (autoMode)
  {
  case 0:
  {
    EMC_TASK_PLAN_RUN pr;

    pr.line = line;
    //         fprintf(stderr, "cmdWriter.setAuto(run) - line: %d\n", line);
    rv = sendCommand(pr);
  }
  break;
  case 1:
  {
    EMC_TASK_PLAN_PAUSE pp;

    //         fputs("cmdWriter.setAuto(pause)\n", stderr);
    rv = sendCommand(pp);
  }
  break;
  case 2:
  {
    EMC_TASK_PLAN_RESUME pr;

    //         fputs("cmdWriter.setAuto(resume)\n", stderr);
    rv = sendCommand(pr);
  }
  break;
  case 3:
  {
    EMC_TASK_PLAN_STEP ps;

    //         fputs("cmdWriter.setAuto(step)\n", stderr);
    rv = sendCommand(ps);
  }
  break;
  case 4:
  {
    EMC_TASK_PLAN_REVERSE pr;

    //         fputs("cmdWriter.setAuto(reverse)\n", stderr);
    rv = sendCommand(pr);
  }
  break;
  case 5:
  {
    EMC_TASK_PLAN_FORWARD pf;

    //         fputs("cmdWriter.setAuto(forward)\n", stderr);
    rv = sendCommand(pf);
  }
  break;
  }
  //  if (rv) fputs("changing interpreters auto mode FAILED!", stderr);
}

/*
 * Class:     com_mindovercnc_linuxcnc_CommandWriter
 * Method:    setBlockDelete
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL Java_com_mindovercnc_linuxcnc_CommandWriter_setBlockDelete(JNIEnv *env, jobject thisObject, jboolean enable)
{
  EMC_TASK_PLAN_SET_BLOCK_DELETE bd;

  if (enable)
    bd.state = 1;
  else
    bd.state = 0;
  sendCommand(bd);
}

/*
 * Class:     com_mindovercnc_linuxcnc_CommandWriter
 * Method:    setFeedOverride
 * Signature: (D)V
 */
JNIEXPORT void JNICALL Java_com_mindovercnc_linuxcnc_CommandWriter_setFeedOverride(JNIEnv *env, jobject thisObject, jdouble rate)
{
  EMC_TRAJ_SET_SCALE ss;

  ss.scale = rate;
  sendCommand(ss);
}

/*
 * Class:     com_mindovercnc_linuxcnc_CommandWriter
 * Method:    setFlood
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL Java_com_mindovercnc_linuxcnc_CommandWriter_setFlood(JNIEnv *env, jobject thisObject, jboolean enable)
{
  if (enable)
  {
    EMC_COOLANT_FLOOD_ON fo;

    sendCommand(fo);
  }
  else
  {
    EMC_COOLANT_FLOOD_OFF fo;

    sendCommand(fo);
  }
}

/*
 * Class:     com_mindovercnc_linuxcnc_CommandWriter
 * Method:    setMist
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL Java_com_mindovercnc_linuxcnc_CommandWriter_setMist(JNIEnv *env, jobject thisObject, jboolean enable)
{
  if (enable)
  {
    EMC_COOLANT_MIST_ON mo;

    sendCommand(mo);
  }
  else
  {
    EMC_COOLANT_MIST_OFF mo;

    sendCommand(mo);
  }
}

/*
 * Class:     com_mindovercnc_linuxcnc_CommandWriter
 * Method:    setOptionalStop
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL Java_com_mindovercnc_linuxcnc_CommandWriter_setOptionalStop(JNIEnv *env, jobject thisObject, jboolean enable)
{
  EMC_TASK_PLAN_SET_OPTIONAL_STOP os;

  os.state = enable;
  sendCommand(os);
}

/*
 * Class:     com_mindovercnc_linuxcnc_CommandWriter
 * Method:    setRapidOverride
 * Signature: (D)V
 */
JNIEXPORT void JNICALL Java_com_mindovercnc_linuxcnc_CommandWriter_setRapidOverride(JNIEnv *env, jobject thisObject, jdouble rate)
{
  EMC_TRAJ_SET_RAPID_SCALE rs;

  rs.scale = rate;
  sendCommand(rs);
}

/*
 * Class:     com_mindovercnc_linuxcnc_CommandWriter
 * Method:    setSpindle
 * Signature: (ZI)V
 */
JNIEXPORT void JNICALL Java_com_mindovercnc_linuxcnc_CommandWriter_setSpindle(JNIEnv *env, jobject thisObject, jboolean enable, jint speed, jint direction)
{
  if (enable)
  {
    EMC_SPINDLE_ON so;

    so.speed = direction * speed;
    so.spindle = 0;
    sendCommand(so);
  }
  else
  {
    EMC_SPINDLE_OFF so;

    so.spindle = 0;
    sendCommand(so);
  }
}

/*
 * Class:     com_mindovercnc_linuxcnc_CommandWriter
 * Method:    setSpindleOverride
 * Signature: (D)V
 */
JNIEXPORT void JNICALL Java_com_mindovercnc_linuxcnc_CommandWriter_setSpindleOverride(JNIEnv *env, jobject thisObject, jdouble rate)
{
  EMC_TRAJ_SET_SPINDLE_SCALE ss;

  ss.spindle = 0;
  ss.scale = rate;
  sendCommand(ss);
}

JNIEXPORT void JNICALL Java_com_mindovercnc_linuxcnc_CommandWriter_setFeedHold(JNIEnv *env, jobject thisObject, jint mode)
{
  EMC_TRAJ_SET_FH_ENABLE fh;
  fh.mode = mode;
  sendCommand(fh);
}

/*
 * Class:     com_mindovercnc_linuxcnc_CommandWriter
 * Method:    setTaskMode
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_mindovercnc_linuxcnc_CommandWriter_setTaskMode(JNIEnv *env, jobject thisObject, jint mode)
{
  EMC_TASK_SET_MODE sm;

  sm.mode = (EMC_TASK_MODE_ENUM)mode;
  sendCommand(sm);
}

/*
 * Class:     com_mindovercnc_linuxcnc_CommandWriter
 * Method:    setTaskState
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_mindovercnc_linuxcnc_CommandWriter_setTaskState(JNIEnv *env, jobject thisObject, jint state)
{
  EMC_TASK_SET_STATE ss;

  ss.state = (EMC_TASK_STATE_ENUM)state;
  sendCommand(ss);
}

/*
 * Class:     com_mindovercnc_linuxcnc_CommandWriter
 * Method:    taskAbort
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_mindovercnc_linuxcnc_CommandWriter_taskAbort(JNIEnv *env, jobject thisObject)
{
  EMC_TASK_ABORT ta;

  sendCommand(ta);
}
