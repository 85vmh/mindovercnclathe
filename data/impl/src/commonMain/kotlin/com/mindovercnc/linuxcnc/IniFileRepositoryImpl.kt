package com.mindovercnc.linuxcnc

import com.mindovercnc.model.G53AxisLimits
import com.mindovercnc.model.IniFile
import com.mindovercnc.repository.IniFileRepository
import okio.Path.Companion.toPath

/** Implementation for [IniFileRepository]. */
class IniFileRepositoryImpl : IniFileRepository {
    override fun getIniFile(): IniFile {
        /* no-op */
        return IniFile(
            subroutinePath = "",
            programDir = "".toPath(),
            parameterFile = "".toPath(),
            toolTableFile = "".toPath(),
            joints = emptyList()
        )
    }

    override fun getActiveLimits(): G53AxisLimits {
        /* no-op */
        return G53AxisLimits()
    }

    override fun setCustomG53AxisLimits(g53AxisLimits: G53AxisLimits) {
        /* no-op */
    }

    override fun toggleCustomLimits() {
        /* no-op */
    }

    override fun isCustomLimitsActive(): Boolean {
        return false
    }
}