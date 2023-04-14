/*
 * **************************************************************************
 *
 *  file:       com_mindovercnc_linuxcnc_ErrorReader.cc
 *  project:    GUI for linuxcnc
 *  subproject: C-stub to access nml-structure in shared memory
 *  purpose:    read error messages and convert them into java elements
 *  created:    8.10.2019 by Django Reinhard
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
#include <nml.hh>
#include <nml_oi.hh>
#include <stdio.h>
#include <cstring>
#include <config.h>

#include  <com_mindovercnc_linuxcnc_ErrorReader.h>


static NML* ec;


/*
 * Class:     com_mindovercnc_linuxcnc_ErrorReader
 * Method:    fetchMessage
 * Signature: ()Lde/schwarzrot/system/SystemMessage;
 *
 * Sub Types: Lde/schwarzrot/system/SystemMessage;
 *            Lde/schwarzrot/system/SystemMessage/MessageType;
 */
JNIEXPORT jobject JNICALL Java_com_mindovercnc_linuxcnc_ErrorReader_fetchMessage(JNIEnv *env, jobject thisObject) {
  if (!ec || !ec->valid()) {
//     fprintf(stderr, "ERROR: error buffer is invalid!");
     return NULL;
     }
  NMLTYPE type = ec->read();

  if (!type) return NULL;
  char error_buffer[LINELEN];

  jclass clSysMsg  = env->FindClass("com/mindovercnc/linuxcnc/model/SystemMessage");
  jclass clMsgType = env->FindClass("com/mindovercnc/linuxcnc/model/SystemMessage$MessageType");
  jfieldID fEnumMsgType;

  switch (type) {
    case EMC_OPERATOR_ERROR_TYPE: {
         fEnumMsgType = env->GetStaticFieldID(clMsgType
                                            , "OperatorError"
                                            , "Lcom/mindovercnc/linuxcnc/model/SystemMessage$MessageType;");
         strncpy(error_buffer, ((EMC_OPERATOR_ERROR*)ec->get_address())->error, LINELEN-1);
         } break;
    case EMC_OPERATOR_TEXT_TYPE: {
         fEnumMsgType = env->GetStaticFieldID(clMsgType
                                            , "OperatorText"
                                            , "Lcom/mindovercnc/linuxcnc/model/SystemMessage$MessageType;");
         strncpy(error_buffer, ((EMC_OPERATOR_TEXT*)ec->get_address())->text, LINELEN-1);
         } break;
    case EMC_OPERATOR_DISPLAY_TYPE: {
         fEnumMsgType = env->GetStaticFieldID(clMsgType
                                            , "OperatorDisplay"
                                            , "Lcom/mindovercnc/linuxcnc/model/SystemMessage$MessageType;");
         strncpy(error_buffer, ((EMC_OPERATOR_DISPLAY*)ec->get_address())->display, LINELEN-1);
         } break;
    case NML_ERROR_TYPE: {
         fEnumMsgType = env->GetStaticFieldID(clMsgType
                                            , "NMLError"
                                            , "Lcom/mindovercnc/linuxcnc/model/SystemMessage$MessageType;");
         strncpy(error_buffer, ((NML_ERROR*)ec->get_address())->error, LINELEN-1);
         } break;
    case NML_TEXT_TYPE: {
         fEnumMsgType = env->GetStaticFieldID(clMsgType
                                            , "NMLText"
                                            , "Lcom/mindovercnc/linuxcnc/model/SystemMessage$MessageType;");
         strncpy(error_buffer, ((NML_TEXT*)ec->get_address())->text, LINELEN-1);
         } break;
    case NML_DISPLAY_TYPE: {
         fEnumMsgType = env->GetStaticFieldID(clMsgType
                                            , "NMLDisplay"
                                            , "Lcom/mindovercnc/linuxcnc/model/SystemMessage$MessageType;");
         strncpy(error_buffer, ((NML_DISPLAY*)ec->get_address())->display, LINELEN-1);
         } break;
    default: {
         fEnumMsgType = env->GetStaticFieldID(clMsgType
                                            , "NMLError"
                                            , "Lcom/mindovercnc/linuxcnc/model/SystemMessage$MessageType;");
         sprintf(error_buffer, "unrecognized error %d", type);
         } break;
    }
  error_buffer[LINELEN-1]  = 0;
  jstring  errorMessage    = env->NewStringUTF(error_buffer);
  jobject  enumMessageType = env->GetStaticObjectField(clMsgType, fEnumMsgType);
  jobject  newSystemMsg    = env->AllocObject(clSysMsg);
  jfieldID fMsg            = env->GetFieldID(clSysMsg
                                           , "message"
                                           , "Ljava/lang/String;");
  jfieldID fType           = env->GetFieldID(clSysMsg
                                           , "type"
                                           , "Lcom/mindovercnc/linuxcnc/model/SystemMessage$MessageType;");
  env->SetObjectField(newSystemMsg, fMsg,  errorMessage);
  env->SetObjectField(newSystemMsg, fType, enumMessageType);

  return newSystemMsg;
  }


/*
 * Class:     com_mindovercnc_linuxcnc_ErrorReader
 * Method:    init
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_mindovercnc_linuxcnc_ErrorReader_init(JNIEnv *env, jobject thisObject) {
  const char* nmlFile = EMC2_DEFAULT_NMLFILE;

  ec = new NML(emcFormat, "emcError", "xemc", nmlFile);
  if (!ec || !ec->valid()) {
     delete ec;
     ec = NULL;

     return -1;
     }
  if (ec->error_type) {
//     fprintf(stderr, "ERROR creating error Channel: #%d\n", ec->error_type);
     return -2;
     }
  return 0;
  }

