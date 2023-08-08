package usecase

import com.ionspin.kotlin.bignum.decimal.DecimalMode
import com.ionspin.kotlin.bignum.decimal.RoundingMode
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import com.mindovercnc.data.linuxcnc.CncStatusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import usecase.model.ActiveCode

class ActiveCodesUseCase(
    private val statusRepository: CncStatusRepository,
) {
    fun getActiveCodes(): Flow<List<ActiveCode>> {
        return statusRepository
            .cncStatusFlow
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

    private fun Float.stripZeros(): String {
        return this.toBigDecimal(decimalMode = DecimalMode(scale = 1, roundingMode = RoundingMode.ROUND_HALF_CEILING))
            .toPlainString()
    }
}
