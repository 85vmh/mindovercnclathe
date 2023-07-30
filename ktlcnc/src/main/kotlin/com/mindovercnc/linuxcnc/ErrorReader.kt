package com.mindovercnc.linuxcnc

import com.mindovercnc.linuxcnc.model.SystemMessage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import java.util.*

/*
 * **************************************************************************
 *
 *  file:       com.mindovercnc.linuxcnc.ErrorReader.java
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
class ErrorReader {
    private var isBackendAvailable = false

    private var initialised = false

    private external fun init(): Int
    private external fun fetchMessage(): SystemMessage?

    fun initialise() {
        if (!initialised) {
            isBackendAvailable = init() >= 0
            initialised = true
        }
    }

    fun refresh(interval: Long) = flow {
        while (true) {
            emit(fetchMessage()?.apply { time = Date() })
            delay(interval)
        }
    }.onStart { initialise() }
}
