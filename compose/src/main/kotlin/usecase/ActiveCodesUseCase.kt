package usecase

import com.mindovercnc.repository.CncStatusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import usecase.model.ActiveCode
import java.math.BigDecimal
import java.math.RoundingMode

class ActiveCodesUseCase(
    private val statusRepository: CncStatusRepository,
) {
    fun getActiveCodes(): Flow<List<ActiveCode>> {
        return statusRepository.cncStatusFlow()
            .map { it.taskStatus.activeCodes }
            .map { activeCodes ->
                val result = mutableListOf<ActiveCode>()
                result.addAll(activeCodes.gCodes.sorted().map {
                    ActiveCode(it, "G${it.stripZeros()}", ActiveCode.Type.GCode)
                })
                result.addAll(activeCodes.mCodes.sorted().map {
                    ActiveCode(it, "M${it.stripZeros()}", ActiveCode.Type.MCode)
                })
                result
            }
    }

    fun getCodeDescription(code: ActiveCode): String {
        return "Description: $code"
    }

    private fun Float.stripZeros(): String {
        return BigDecimal(this.toDouble()).setScale(1, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString()
    }
}