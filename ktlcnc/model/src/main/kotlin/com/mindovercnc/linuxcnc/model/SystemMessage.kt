package com.mindovercnc.linuxcnc.model

import java.util.*
import java.util.regex.Pattern

/*
 * **************************************************************************
 *
 *  file:       com.mindovercnc.linuxcnc.model.SystemMessage.java
 *  project:    GUI for linuxcnc
 *  subproject: graphical application frontend
 *  purpose:    create a smart application, that assists in managing
 *              control of cnc-machines
 *  created:    7.10.2019 by Django Reinhard
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
data class SystemMessage
@JvmOverloads
constructor(
  val message: String,
  val type: MessageType = MessageType.CommandLog,
  var time: Date = Date()
) : Comparable<SystemMessage> {

  enum class MessageType(val typeNum: Int) {
    NMLError(1),
    NMLText(2),
    NMLDisplay(3),
    CommandLog(7),
    OperatorError(11),
    OperatorText(12),
    OperatorDisplay(13)
  }

  override fun compareTo(other: SystemMessage): Int {
    return when (other.time.time) {
      time.time -> other.type.compareTo(type)
      else -> other.time.compareTo(time)
    }
  }

  companion object {
    @JvmStatic
    fun parseMessage(source: String): SystemMessage? {
      val parts = source.split(Pattern.compile("\\s+"), 3).toTypedArray()
      if (parts.size < 3) return null
      val typeNum = parts[0].toInt()
      val time = Date(parts[1].toLong())
      val mt =
        when (typeNum) {
          1 -> MessageType.NMLError
          2 -> MessageType.NMLText
          3 -> MessageType.NMLDisplay
          7 -> MessageType.CommandLog
          11 -> MessageType.OperatorError
          12 -> MessageType.OperatorText
          13 -> MessageType.OperatorDisplay
          else -> MessageType.CommandLog
        }
      return SystemMessage(parts[2], mt, time)
    }
  }
}
