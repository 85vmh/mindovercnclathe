package com.mindovercnc.linuxcnc

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import java.nio.ByteBuffer
import java.nio.ByteOrder

/*
 * **************************************************************************
 *
 *  file:       com.mindovercnc.linuxcnc.StatusReader.java
 *  project:    GUI for linuxcnc
 *  subproject: graphical application frontend
 *  purpose:    create a smart application, that assists in managing
 *              control of cnc-machines
 *  created:    2.9.2019 by Django Reinhard
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
class StatusReader {
    private var statusBuffer: ByteBuffer? = null

    private var initialised = false

    private fun initialise() {
        if (!initialised) {
            statusBuffer = init()
            statusBuffer?.order(ByteOrder.LITTLE_ENDIAN)
            initialised = true
        }
    }

    fun refresh(interval: Long): Flow<ByteBuffer?> {
        return flow {
            while (true) {
                readStatus()
                emit(statusBuffer)
                delay(interval)
            }
        }
            .onStart { initialise() }
    }

    private external fun init(): ByteBuffer?
    private external fun readStatus(): Int
    external fun getString(offset: Int, length: Int): String?
}
