package com.mindovercnc.linuxcnc.domain

import com.mindovercnc.data.linuxcnc.CncStatusRepository
import com.mindovercnc.linuxcnc.domain.model.ActiveCode
import com.mindovercnc.linuxcnc.format.stripZeros
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ActiveCodesUseCase(
    private val statusRepository: CncStatusRepository,
) {
    fun getActiveCodes(): Flow<List<ActiveCode>> {
        return statusRepository.cncStatusFlow
            .map { it.task_status!!.activeCodes!! }
            .map { activeCodes ->
                val result = mutableListOf<ActiveCode>()
                result.addAll(
                    activeCodes.g_codes.sorted().map {
                        ActiveCode(
                            value = it,
                            stringRepresentation = "G${it.stripZeros()}",
                            type = ActiveCode.Type.GCode
                        )
                    }
                )
                result.addAll(
                    activeCodes.m_codes.sorted().map {
                        ActiveCode(
                            value = it,
                            stringRepresentation = "M${it.stripZeros()}",
                            type = ActiveCode.Type.MCode
                        )
                    }
                )
                result
            }
    }

    fun getCodeDescription(code: ActiveCode): String {
        return "Description: $code"
    }
}
