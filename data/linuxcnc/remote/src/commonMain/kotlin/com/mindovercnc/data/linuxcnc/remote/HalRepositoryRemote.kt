package com.mindovercnc.data.linuxcnc.remote

import com.mindovercnc.data.linuxcnc.HalRepository
import com.mindovercnc.model.JoystickPosition
import com.mindovercnc.model.JoystickStatus
import com.mindovercnc.model.SpindleSwitchStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import mu.KotlinLogging
import ro.dragossusi.proto.linuxcnc.CreateComponentRequest
import ro.dragossusi.proto.linuxcnc.CreatePinRequest
import ro.dragossusi.proto.linuxcnc.LinuxCncClient
import ro.dragossusi.proto.linuxcnc.hal.HalComponent
import ro.dragossusi.proto.linuxcnc.hal.HalPin
import ro.dragossusi.proto.linuxcnc.hal.HalPinDir
import ro.dragossusi.proto.linuxcnc.hal.HalPinType

/** Implementation for [HalRepository]. */
class HalRepositoryRemote(private val linuxCncGrpc: LinuxCncClient) : HalRepository {
    private val halComponent: HalComponent?
    private var pinJoystickXPlus: HalPin? = null
    private var pinJoystickXMinus: HalPin? = null
    private var pinJoystickZPlus: HalPin? = null
    private var pinJoystickZMinus: HalPin? = null
    private var pinJoystickRapid: HalPin? = null
    private var pinIsPowerFeeding: HalPin? = null
    private var pinCycleStart: HalPin? = null
    private var pinCycleStop: HalPin? = null
    private var pinSpindleSwitchRevIn: HalPin? = null
    private var pinSpindleSwitchFwdIn: HalPin? = null
    private var pinSpindleStarted: HalPin? = null
    private var pinJogIncrementValue: HalPin? = null
    private var pinSpindleActualRpm: HalPin? = null
    private var pinAxisLimitXMin: HalPin? = null
    private var pinAxisLimitXMax: HalPin? = null
    private var pinAxisLimitZMin: HalPin? = null
    private var pinAxisLimitZMax: HalPin? = null
    private var pinToolChangeToolNo: HalPin? = null
    private var pinToolChangeRequest: HalPin? = null
    private var pinToolChangeResponse: HalPin? = null

    init {
        halComponent = createComponent(ComponentName)
        if (halComponent != null) {
            initPins(halComponent)
        }
    }

    private fun createComponent(name: String): HalComponent? {
        val request = CreateComponentRequest(name = name)
        return try {
            linuxCncGrpc.CreateComponent().executeBlocking(request)
        } catch (e:Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun HalComponent.addPin(name: String, type: HalPinType, dir: HalPinDir): HalPin {
        val request = CreatePinRequest(
            component_id = component_id.toString(), name = name, type = type, dir = dir
        )
        return linuxCncGrpc.CreatePin().executeBlocking(request)
    }

    private fun initPins(component: HalComponent) {
        pinJoystickXPlus = component.addPin(PinJoystickXPlus, HalPinType.TYPE_BIT, HalPinDir.IN)
        pinJoystickXMinus = component.addPin(PinJoystickXMinus, HalPinType.TYPE_BIT, HalPinDir.IN)
        pinJoystickZPlus = component.addPin(PinJoystickZPlus, HalPinType.TYPE_BIT, HalPinDir.IN)
        pinJoystickZMinus = component.addPin(PinJoystickZMinus, HalPinType.TYPE_BIT, HalPinDir.IN)
        pinJoystickRapid = component.addPin(PinJoystickRapid, HalPinType.TYPE_BIT, HalPinDir.IN)
        pinIsPowerFeeding = component.addPin(PinIsPowerFeeding, HalPinType.TYPE_BIT, HalPinDir.OUT)
        pinSpindleSwitchRevIn = component.addPin(PinSpindleSwitchRevIn, HalPinType.TYPE_BIT, HalPinDir.IN)
        pinSpindleSwitchFwdIn = component.addPin(PinSpindleSwitchFwdIn, HalPinType.TYPE_BIT, HalPinDir.IN)
        pinSpindleStarted = component.addPin(PinSpindleStarted, HalPinType.TYPE_BIT, HalPinDir.OUT)
        pinCycleStart = component.addPin(PinCycleStart, HalPinType.TYPE_BIT, HalPinDir.IN)
        pinCycleStop = component.addPin(PinCycleStop, HalPinType.TYPE_BIT, HalPinDir.IN)
        pinJogIncrementValue = component.addPin(PinJogIncrement, HalPinType.FLOAT, HalPinDir.IN)
        pinSpindleActualRpm = component.addPin(PinSpindleActualRpm, HalPinType.FLOAT, HalPinDir.IN)

        pinAxisLimitXMin = component.addPin(PinAxisLimitXMin, HalPinType.FLOAT, HalPinDir.OUT)
        pinAxisLimitXMax = component.addPin(PinAxisLimitXMax, HalPinType.FLOAT, HalPinDir.OUT)
        pinAxisLimitZMin = component.addPin(PinAxisLimitZMin, HalPinType.FLOAT, HalPinDir.OUT)
        pinAxisLimitZMax = component.addPin(PinAxisLimitZMax, HalPinType.FLOAT, HalPinDir.OUT)

        pinToolChangeToolNo = component.addPin(PinToolChangeToolNo, HalPinType.S32, HalPinDir.IN)
        pinToolChangeRequest =
            component.addPin(PinToolChangeRequest, HalPinType.TYPE_BIT, HalPinDir.IN)
        pinToolChangeResponse =
            component.addPin(PinToolChangeResponse, HalPinType.TYPE_BIT, HalPinDir.OUT)


        // TODO setReady
//        component.setReady(component.componentId)
    }

    override fun getJoystickStatus(): Flow<JoystickStatus> {
        fun neutralFlow() = flowOf(JoystickStatus(JoystickStatus.Position.Neutral)).distinctUntilChanged()

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
            val isRapidValue = isRapid.bool_value!!
            when {
                xPlus.bool_value!! -> JoystickStatus(JoystickStatus.Position.XPlus, isRapidValue)
                xMinus.bool_value!! -> JoystickStatus(JoystickStatus.Position.XMinus, isRapidValue)
                zPlus.bool_value!! -> JoystickStatus(JoystickStatus.Position.ZPlus, isRapidValue)
                zMinus.bool_value!! -> JoystickStatus(JoystickStatus.Position.ZMinus, isRapidValue)
                else -> JoystickStatus(JoystickStatus.Position.Neutral, false)
            }
        }.distinctUntilChanged()
    }

    private fun HalPin.valueFlow(refreshRate: Long) = flow {
        while (true) {
            emit(linuxCncGrpc.GetPinValue().executeBlocking(this@valueFlow))
            delay(refreshRate)
        }
    }.filterNotNull()

    private fun HalPin.boolValueFlow(refreshRate: Long): Flow<Boolean> {
        check(type == HalPinType.TYPE_BIT)
        return valueFlow(refreshRate)
            .map { it.bool_value!! }
    }

    private fun HalPin.floatValueFlow(refreshRate: Long): Flow<Float> {
        check(type == HalPinType.FLOAT)
        return valueFlow(refreshRate)
            .map { it.float_value!! }
    }

    private fun HalPin.intValueFlow(refreshRate: Long): Flow<Int> {
        check(type == HalPinType.S32)
        return valueFlow(refreshRate)
            .map { it.int_value!! }
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
                xPlus.bool_value!! -> JoystickPosition.XPlus
                xMinus.bool_value!! -> JoystickPosition.XMinus
                zPlus.bool_value!! -> JoystickPosition.ZPlus
                zMinus.bool_value!! -> JoystickPosition.ZMinus
                else -> JoystickPosition.Neutral
            }
        }.distinctUntilChanged()
    }

    override fun getJoystickRapidState(): Flow<Boolean> {
        return pinJoystickRapid?.run {
            valueFlow(RefreshRate).map { it.bool_value!! }.distinctUntilChanged()
        } ?: flowOf(false)
    }

    override fun setPowerFeedingStatus(isActive: Boolean) {
        pinIsPowerFeeding?.setPinValue(isActive)
    }

    override fun setSpindleStarted(isStarted: Boolean) {
        pinSpindleStarted?.setPinValue(isStarted)
    }


    override fun getSpindleSwitchStatus(): Flow<SpindleSwitchStatus> {
        val revIn = pinSpindleSwitchRevIn?.valueFlow(RefreshRate) ?: return flowOf(SpindleSwitchStatus.NEUTRAL)
        val fwdIn = pinSpindleSwitchFwdIn?.valueFlow(RefreshRate) ?:return flowOf(SpindleSwitchStatus.NEUTRAL)
        return combine(revIn, fwdIn) { isRev, isFwd ->
            when {
                isRev.bool_value == true -> SpindleSwitchStatus.REV
                isFwd.bool_value == true -> SpindleSwitchStatus.FWD
                else -> SpindleSwitchStatus.NEUTRAL
            }
        }.distinctUntilChanged()
    }

    override fun getCycleStartStatus(): Flow<Boolean> {
        return pinCycleStart?.boolValueFlow(RefreshRate)?.distinctUntilChanged() ?: flowOf(false)
    }

    override fun getCycleStopStatus(): Flow<Boolean> {
        return pinCycleStop?.boolValueFlow(RefreshRate)?.distinctUntilChanged() ?: flowOf(false)
    }

    override fun actualSpindleSpeed(): Flow<Float> {
        return pinSpindleActualRpm?.floatValueFlow(RpmRefreshRate)?.distinctUntilChanged() ?: flowOf(0.0f)
    }

    override fun jogIncrementValue(): Flow<Float> {
        return pinJogIncrementValue?.floatValueFlow(RefreshRate)?.distinctUntilChanged() ?: flowOf(0.0f)
    }

    override fun getToolChangeRequest(): Flow<Boolean> {
        return pinToolChangeRequest?.boolValueFlow(RefreshRate)?.distinctUntilChanged() ?: flowOf(false)
    }

    override fun getToolChangeToolNumber(): Flow<Int> {
        return pinToolChangeToolNo?.intValueFlow(RefreshRate)?.distinctUntilChanged() ?: flowOf(0)
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

    private fun HalPin.setPinValue(value: Boolean) {
        //TODO
        logger.warn { "Setting pin value is not yet supported" }
    }

    private fun HalPin.setPinValue(value: Float) {
        //TODO
        logger.warn { "Setting pin value is not yet supported" }
    }

    companion object {
        private val logger = KotlinLogging.logger("HalRepository")

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
    }
}