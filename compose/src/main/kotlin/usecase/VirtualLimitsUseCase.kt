package usecase

import codegen.Point
import com.mindovercnc.repository.*
import com.mindovercnc.model.*
import extensions.toFixedDigits
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import usecase.model.VirtualLimits

class VirtualLimitsUseCase(
    private val scope: CoroutineScope,
    private val statusRepository: CncStatusRepository,
    private val halRepository: HalRepository,
    private val settingsRepository: SettingsRepository,
    private val iniFileRepository: IniFileRepository,
    private val activeLimitsRepository: ActiveLimitsRepository
) {

    val hasToolLoaded = statusRepository.cncStatusFlow().map { it.currentToolNo != 0 }.distinctUntilChanged()

    init {
        combine(
            hasToolLoaded,
            activeLimitsRepository.isLimitsActive,
        ) { hasTool, isActive -> hasTool && isActive }
            .filter { it }
            .onEach {
                println("---Apply for tool: $it")
                setCustomLimits(getSavedVirtualLimits().first())
                applyActiveLimits()
            }
            .launchIn(scope)

        applyActiveLimits()
    }

    fun isLimitsActive() = activeLimitsRepository.isLimitsActive

    suspend fun setLimitsActive(active: Boolean) {
        activeLimitsRepository.setActive(active)
        if (active) {
            setCustomLimits(getSavedVirtualLimits().first())
        }
        iniFileRepository.toggleCustomLimits()
        applyActiveLimits()
    }

    fun getSavedVirtualLimits() = combine(
        settingsRepository.flow(DoubleKey.VirtualLimitXMinus, Double.MIN_VALUE),
        settingsRepository.flow(DoubleKey.VirtualLimitXPlus, Double.MAX_VALUE),
        settingsRepository.flow(DoubleKey.VirtualLimitZMinus, Double.MIN_VALUE),
        settingsRepository.flow(DoubleKey.VirtualLimitZPlus, Double.MAX_VALUE),
        settingsRepository.flow(BooleanKey.VirtualLimitXMinusActive),
        settingsRepository.flow(BooleanKey.VirtualLimitXPlusActive),
        settingsRepository.flow(BooleanKey.VirtualLimitZMinusActive),
        settingsRepository.flow(BooleanKey.VirtualLimitZPlusActive),
        settingsRepository.flow(BooleanKey.LimitZPlusIsToolRelated)
    ) { values ->
        VirtualLimits(
            xMinus = values[0] as Double,
            xPlus = values[1] as Double,
            zMinus = values[2] as Double,
            zPlus = values[3] as Double,
            xMinusActive = values[4] as Boolean,
            xPlusActive = values[5] as Boolean,
            zMinusActive = values[6] as Boolean,
            zPlusActive = values[7] as Boolean,
            zPlusIsToolRelated = values[8] as Boolean
        )
    }

    fun saveVirtualLimits(limits: VirtualLimits) {
        settingsRepository.apply {
            put(DoubleKey.VirtualLimitXMinus, limits.xMinus)
            put(DoubleKey.VirtualLimitXPlus, limits.xPlus)
            put(DoubleKey.VirtualLimitZMinus, limits.zMinus)
            put(DoubleKey.VirtualLimitZPlus, limits.zPlus)
            put(BooleanKey.VirtualLimitXMinusActive, limits.xMinusActive)
            put(BooleanKey.VirtualLimitXPlusActive, limits.xPlusActive)
            put(BooleanKey.VirtualLimitZMinusActive, limits.zMinusActive)
            put(BooleanKey.VirtualLimitZPlusActive, limits.zPlusActive)
            put(BooleanKey.LimitZPlusIsToolRelated, limits.zPlusIsToolRelated)
        }
        scope.launch {
            setCustomLimits(limits)
            applyActiveLimits()
        }
    }

    private suspend fun setCustomLimits(limits: VirtualLimits) {
        val relativeToolPosition = statusRepository.cncStatusFlow()
            .map { it.getRelativeToolPosition() }
            .map { Point(it.x * 2, it.z) } // *2 due to diameter mode
            .first()

        val g53XMinus = relativeToolPosition.x + limits.xMinus
        val g53XPlus = relativeToolPosition.x + limits.xPlus
        val g53ZMinus = relativeToolPosition.z + limits.zMinus
        val g53ZPlus = when (limits.zPlusIsToolRelated) {
            true -> relativeToolPosition.z + limits.zPlus
            false -> limits.zPlus
        }

        val g53AxisLimits = G53AxisLimits(
            xMinLimit = if (limits.xMinusActive) (g53XMinus / 2).toFixedDigits() else null,
            xMaxLimit = if (limits.xPlusActive) (g53XPlus / 2).toFixedDigits() else null,
            zMinLimit = if (limits.zMinusActive) g53ZMinus.toFixedDigits() else null,
            zMaxLimit = if (limits.zPlusActive) g53ZPlus.toFixedDigits() else null
        )
        iniFileRepository.setCustomG53AxisLimits(g53AxisLimits)
    }

    private fun applyActiveLimits() {
        with(iniFileRepository.getActiveLimits()) {
            halRepository.setAxisLimitXMin(this.xMinLimit!!)
            halRepository.setAxisLimitXMax(this.xMaxLimit!!)
            halRepository.setAxisLimitZMin(this.zMinLimit!!)
            halRepository.setAxisLimitZMax(this.zMaxLimit!!)
            println("---HAL Apply: $this")
        }
    }
}