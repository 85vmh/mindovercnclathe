package usecase

import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.linuxcnc.model.CncStatus
import com.mindovercnc.model.JoystickStatus
import com.mindovercnc.model.SpindleSwitchStatus
import com.mindovercnc.model.UiMessage
import com.mindovercnc.repository.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ManualTurningUseCaseTest {
  private val joystickStatusFlow = MutableStateFlow(JoystickStatus(JoystickStatus.Position.Neutral))
  private val spindleSwitchStatusFlow = MutableStateFlow(SpindleSwitchStatus.NEUTRAL)
  private val cycleStopStatusFlow = MutableStateFlow(false)
  private val cycleStartStatusFlow = MutableStateFlow(false)
  private val actualSpindleSpeed = MutableStateFlow(1f)
  private val jogIncrementValue = MutableStateFlow(1f)
  private val cncStatusFlow = MutableSharedFlow<CncStatus>()

  private val statusRepository =
    mockk<CncStatusRepository> { every { cncStatusFlow() } returns cncStatusFlow }
  private val commandRepository = mockk<CncCommandRepository>()
  private val messagesRepository = mockk<MessagesRepository>()
  private val halRepository =
    mockk<HalRepository> {
      every { getJoystickStatus() } returns joystickStatusFlow
      every { getSpindleSwitchStatus() } returns spindleSwitchStatusFlow
      every { getCycleStopStatus() } returns cycleStopStatusFlow
      every { getCycleStartStatus() } returns cycleStartStatusFlow
      every { actualSpindleSpeed() } returns actualSpindleSpeed
      every { jogIncrementValue() } returns jogIncrementValue
    }
  private val settingsRepository = mockk<SettingsRepository>()
  private val iniFileRepository = mockk<IniFileRepository>()

  private lateinit var testSubject: ManualTurningUseCase

  private val mainThreadSurrogate = UnconfinedTestDispatcher()

  @Before
  fun setUp() {
    Dispatchers.setMain(mainThreadSurrogate)
    testSubject =
      ManualTurningUseCase(
        ioDispatcher = IoDispatcher(mainThreadSurrogate),
        cncStatusRepository = statusRepository,
        commandRepository = commandRepository,
        messagesRepository = messagesRepository,
        halRepository = halRepository,
        settingsRepository = settingsRepository,
        iniFileRepository = iniFileRepository
      )
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
  }

  @Test
  fun `Test Something`() = runTest {
    println("start")
    joystickStatusFlow.emit(JoystickStatus(JoystickStatus.Position.XMinus, false))

    verify { messagesRepository.pushMessage(UiMessage.JoystickCannotFeedWithSpindleOff) }
  }
}
