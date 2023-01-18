package com.mindovercnc.linuxcnc

import com.mindovercnc.linuxcnc.model.*
import com.mindovercnc.model.JoystickPosition
import com.mindovercnc.model.JoystickStatus
import com.mindovercnc.model.SpindleSwitchStatus
import com.mindovercnc.repository.HalRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

private const val RefreshRate = 5L
private const val RpmRefreshRate = 300L
private const val ComponentName = "weiler-e30"
private const val PinJoystickXPlus = "joystick-x-plus"
private const val PinJoystickXMinus = "joystick-x-minus"
private const val PinJoystickZPlus = "joystick-z-plus"
private const val PinJoystickZMinus = "joystick-z-minus"
private const val PinJoystickRapid = "joystick-rapid"
private const val PinIsPowerFeeding = "is-power-feeding"
private const val PinCycleStart = "cycle-start"
private const val PinCycleStop = "cycle-stop"
private const val PinSpindleSwitchRevIn = "spindle.switch-rev-in"
private const val PinSpindleSwitchFwdIn = "spindle.switch-fwd-in"
private const val PinSpindleStarted = "spindle.started"
private const val PinSpindleActualRpm = "spindle.actual-rpm"
private const val PinJogIncrement = "jog-increment-value"
private const val PinToolChangeToolNo = "tool-change.number"
private const val PinToolChangeRequest = "tool-change.change"
private const val PinToolChangeResponse = "tool-change.changed"
private const val PinAxisLimitXMin = "axis-limits.x-min"
private const val PinAxisLimitXMax = "axis-limits.x-max"
private const val PinAxisLimitZMin = "axis-limits.z-min"
private const val PinAxisLimitZMax = "axis-limits.z-max"

/** Implementation for [HalRepository]. */
class HalRepositoryImpl : HalRepository {
  private val halHandler = HalHandler()

  private var halComponent: HalComponent? = null
  private var pinJoystickXPlus: HalPin<Boolean>? = null
  private var pinJoystickXMinus: HalPin<Boolean>? = null
  private var pinJoystickZPlus: HalPin<Boolean>? = null
  private var pinJoystickZMinus: HalPin<Boolean>? = null
  private var pinJoystickRapid: HalPin<Boolean>? = null
  private var pinIsPowerFeeding: HalPin<Boolean>? = null
  private var pinCycleStart: HalPin<Boolean>? = null
  private var pinCycleStop: HalPin<Boolean>? = null
  private var pinSpindleSwitchRevIn: HalPin<Boolean>? = null
  private var pinSpindleSwitchFwdIn: HalPin<Boolean>? = null
  private var pinSpindleStarted: HalPin<Boolean>? = null
  private var pinJogIncrementValue: HalPin<Float>? = null
  private var pinSpindleActualRpm: HalPin<Float>? = null
  private var pinAxisLimitXMin: HalPin<Float>? = null
  private var pinAxisLimitXMax: HalPin<Float>? = null
  private var pinAxisLimitZMin: HalPin<Float>? = null
  private var pinAxisLimitZMax: HalPin<Float>? = null
  private var pinToolChangeToolNo: HalPin<Int>? = null
  private var pinToolChangeRequest: HalPin<Boolean>? = null
  private var pinToolChangeResponse: HalPin<Boolean>? = null

  init {
    halComponent = halHandler.createComponent(ComponentName)
    halComponent?.let {
      pinJoystickXPlus =
        it.addPin(PinJoystickXPlus, HalPin.Type.BIT, HalPin.Dir.IN) as? HalPin<Boolean>
      pinJoystickXMinus =
        it.addPin(PinJoystickXMinus, HalPin.Type.BIT, HalPin.Dir.IN) as? HalPin<Boolean>
      pinJoystickZPlus =
        it.addPin(PinJoystickZPlus, HalPin.Type.BIT, HalPin.Dir.IN) as? HalPin<Boolean>
      pinJoystickZMinus =
        it.addPin(PinJoystickZMinus, HalPin.Type.BIT, HalPin.Dir.IN) as? HalPin<Boolean>
      pinJoystickRapid =
        it.addPin(PinJoystickRapid, HalPin.Type.BIT, HalPin.Dir.IN) as? HalPin<Boolean>
      pinIsPowerFeeding =
        it.addPin(PinIsPowerFeeding, HalPin.Type.BIT, HalPin.Dir.OUT) as? HalPin<Boolean>
      pinSpindleSwitchRevIn =
        it.addPin(PinSpindleSwitchRevIn, HalPin.Type.BIT, HalPin.Dir.IN) as? HalPin<Boolean>
      pinSpindleSwitchFwdIn =
        it.addPin(PinSpindleSwitchFwdIn, HalPin.Type.BIT, HalPin.Dir.IN) as? HalPin<Boolean>
      pinSpindleStarted =
        it.addPin(PinSpindleStarted, HalPin.Type.BIT, HalPin.Dir.OUT) as? HalPin<Boolean>
      pinCycleStart = it.addPin(PinCycleStart, HalPin.Type.BIT, HalPin.Dir.IN) as? HalPin<Boolean>
      pinCycleStop = it.addPin(PinCycleStop, HalPin.Type.BIT, HalPin.Dir.IN) as? HalPin<Boolean>
      pinJogIncrementValue =
        it.addPin(PinJogIncrement, HalPin.Type.FLOAT, HalPin.Dir.IN) as? HalPin<Float>
      pinSpindleActualRpm =
        it.addPin(PinSpindleActualRpm, HalPin.Type.FLOAT, HalPin.Dir.IN) as? HalPin<Float>

      pinAxisLimitXMin =
        it.addPin(PinAxisLimitXMin, HalPin.Type.FLOAT, HalPin.Dir.OUT) as? HalPin<Float>
      pinAxisLimitXMax =
        it.addPin(PinAxisLimitXMax, HalPin.Type.FLOAT, HalPin.Dir.OUT) as? HalPin<Float>
      pinAxisLimitZMin =
        it.addPin(PinAxisLimitZMin, HalPin.Type.FLOAT, HalPin.Dir.OUT) as? HalPin<Float>
      pinAxisLimitZMax =
        it.addPin(PinAxisLimitZMax, HalPin.Type.FLOAT, HalPin.Dir.OUT) as? HalPin<Float>

      pinToolChangeToolNo =
        it.addPin(PinToolChangeToolNo, HalPin.Type.S32, HalPin.Dir.IN) as? HalPin<Int>
      pinToolChangeRequest =
        it.addPin(PinToolChangeRequest, HalPin.Type.BIT, HalPin.Dir.IN) as? HalPin<Boolean>
      pinToolChangeResponse =
        it.addPin(PinToolChangeResponse, HalPin.Type.BIT, HalPin.Dir.OUT) as? HalPin<Boolean>

      it.setReady(it.componentId)
    }
  }

  override fun getJoystickStatus(): Flow<JoystickStatus> {
    fun neutralFlow() =
      flowOf(JoystickStatus(JoystickStatus.Position.Neutral)).distinctUntilChanged()
    val pinJoyXPlus = pinJoystickXPlus ?: return neutralFlow()
    val pinJoyXMinus = pinJoystickXMinus ?: return neutralFlow()
    val pinJoyZPlus = pinJoystickZPlus ?: return neutralFlow()
    val pinJoyZMinus = pinJoystickZMinus ?: return neutralFlow()
    val pinJoyRapid = pinJoystickRapid ?: return neutralFlow()

    return combine(
        pinJoyXPlus.valueFlow(RefreshRate),
        pinJoyXMinus.valueFlow(RefreshRate),
        pinJoyZPlus.valueFlow(RefreshRate),
        pinJoyZMinus.valueFlow(RefreshRate),
        pinJoyRapid.valueFlow(RefreshRate),
      ) { xPlus, xMinus, zPlus, zMinus, isRapid ->
        when {
          xPlus -> JoystickStatus(JoystickStatus.Position.XPlus, isRapid)
          xMinus -> JoystickStatus(JoystickStatus.Position.XMinus, isRapid)
          zPlus -> JoystickStatus(JoystickStatus.Position.ZPlus, isRapid)
          zMinus -> JoystickStatus(JoystickStatus.Position.ZMinus, isRapid)
          else -> JoystickStatus(JoystickStatus.Position.Neutral, false)
        }
      }
      .distinctUntilChanged()
  }

  override fun getJoystickPosition(): Flow<JoystickPosition> {
    fun neutralFlow() = flowOf(JoystickPosition.Neutral).distinctUntilChanged()
    val pinJoyXPlus = pinJoystickXPlus ?: return neutralFlow()
    val pinJoyXMinus = pinJoystickXMinus ?: return neutralFlow()
    val pinJoyZPlus = pinJoystickZPlus ?: return neutralFlow()
    val pinJoyZMinus = pinJoystickZMinus ?: return neutralFlow()

    return combine(
        pinJoyXPlus.valueFlow(RefreshRate),
        pinJoyXMinus.valueFlow(RefreshRate),
        pinJoyZPlus.valueFlow(RefreshRate),
        pinJoyZMinus.valueFlow(RefreshRate),
      ) { xPlus, xMinus, zPlus, zMinus ->
        when {
          xPlus -> JoystickPosition.XPlus
          xMinus -> JoystickPosition.XMinus
          zPlus -> JoystickPosition.ZPlus
          zMinus -> JoystickPosition.ZMinus
          else -> JoystickPosition.Neutral
        }
      }
      .distinctUntilChanged()
  }

  override fun getJoystickRapidState(): Flow<Boolean> {
    return pinJoystickRapid?.valueFlow(RefreshRate)?.distinctUntilChanged() ?: flowOf(false)
  }

  override fun setPowerFeedingStatus(isActive: Boolean) {
    pinIsPowerFeeding?.setPinValue(isActive)
  }

  override fun setSpindleStarted(isStarted: Boolean) {
    pinSpindleStarted?.setPinValue(isStarted)
  }

  override fun getSpindleSwitchStatus(): Flow<SpindleSwitchStatus> {
    if (pinSpindleSwitchRevIn != null && pinSpindleSwitchFwdIn != null) {
      return combine(
          pinSpindleSwitchRevIn!!.valueFlow(RefreshRate),
          pinSpindleSwitchFwdIn!!.valueFlow(RefreshRate)
        ) { isRev, isFwd ->
          when {
            isRev -> SpindleSwitchStatus.REV
            isFwd -> SpindleSwitchStatus.FWD
            else -> SpindleSwitchStatus.NEUTRAL
          }
        }
        .distinctUntilChanged()
    } else {
      return flowOf(SpindleSwitchStatus.NEUTRAL)
    }
  }

  override fun getCycleStartStatus(): Flow<Boolean> {
    return pinCycleStart?.valueFlow(RefreshRate)?.distinctUntilChanged() ?: flowOf(false)
  }

  override fun getCycleStopStatus(): Flow<Boolean> {
    return pinCycleStop?.valueFlow(RefreshRate)?.distinctUntilChanged() ?: flowOf(false)
  }

  override fun actualSpindleSpeed(): Flow<Float> {
    return pinSpindleActualRpm?.valueFlow(RpmRefreshRate)?.distinctUntilChanged() ?: flowOf(0.0f)
  }

  override fun jogIncrementValue(): Flow<Float> {
    return pinJogIncrementValue?.valueFlow(RefreshRate)?.distinctUntilChanged() ?: flowOf(0.0f)
  }

  override fun getToolChangeRequest(): Flow<Boolean> {
    return pinToolChangeRequest?.valueFlow(RefreshRate)?.distinctUntilChanged() ?: flowOf(false)
  }

  override fun getToolChangeToolNumber(): Flow<Int> {
    return pinToolChangeToolNo?.valueFlow(RefreshRate)?.distinctUntilChanged() ?: flowOf(0)
  }

  override suspend fun setToolChangedResponse() {
    pinToolChangeResponse?.setPinValue(true)
    delay(100L)
    pinToolChangeResponse?.setPinValue(false)
  }

  override fun setAxisLimitXMin(value: Double) {
    pinAxisLimitXMin?.setPinValue(value.toFloat())
  }

  override fun setAxisLimitXMax(value: Double) {
    pinAxisLimitXMax?.setPinValue(value.toFloat())
  }

  override fun setAxisLimitZMin(value: Double) {
    pinAxisLimitZMin?.setPinValue(value.toFloat())
  }

  override fun setAxisLimitZMax(value: Double) {
    pinAxisLimitZMax?.setPinValue(value.toFloat())
  }
}
