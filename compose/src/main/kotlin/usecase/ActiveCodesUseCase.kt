package usecase

import com.mindovercnc.repository.CncStatusRepository
import java.math.BigDecimal
import java.math.RoundingMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import usecase.model.ActiveCode

class ActiveCodesUseCase(
  private val statusRepository: CncStatusRepository,
) {
  fun getActiveCodes(): Flow<List<ActiveCode>> {
    return statusRepository
      .cncStatusFlow()
      .map { it.taskStatus.activeCodes }
      .map { activeCodes ->
        val result = mutableListOf<ActiveCode>()
        result.addAll(
          activeCodes.gCodesList.sorted().map {
            ActiveCode(
              value = it,
              stringRepresentation = "G${it.stripZeros()}",
              type = ActiveCode.Type.GCode
            )
          }
        )
        result.addAll(
          activeCodes.mCodesList.sorted().map {
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
    return BigDecimal(this.toDouble())
      .setScale(1, RoundingMode.HALF_UP)
      .stripTrailingZeros()
      .toPlainString()
  }
}
