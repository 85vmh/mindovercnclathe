package com.mindovercnc.linuxcnc.domain

import com.mindovercnc.data.linuxcnc.CncStatusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import linuxcnc.isEstop
import linuxcnc.isHomed
import linuxcnc.isOn

/** Use case to know when the machine is usable */
class MachineUsableUseCase(private val cncStatusRepository: CncStatusRepository) {

    val machineUsableFlow: Flow<Boolean>
        get() =
            cncStatusRepository.cncStatusFlow
                .map { it.task_status!!.isEstop.not() && it.task_status!!.isOn && it.motion_status!!.isHomed(2) }
                .distinctUntilChanged()
}
