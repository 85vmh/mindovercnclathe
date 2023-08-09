package usecase

import codegen.ConversationalProgram
import codegen.TurningOperation
import com.mindovercnc.data.linuxcnc.CncCommandRepository
import com.mindovercnc.data.linuxcnc.CncStatusRepository
import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.dispatchers.createScope
import com.mindovercnc.repository.FileSystemRepository
import com.mindovercnc.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.Clock

@Deprecated("This is a deprecated class")
class ConversationalUseCase(
    ioDispatcher: IoDispatcher,
    private val clock: Clock,
    private val statusRepository: CncStatusRepository,
    private val commandRepository: CncCommandRepository,
    private val settingsRepository: SettingsRepository,
    private val fileSystemRepository: FileSystemRepository
) {
    private val scope = ioDispatcher.createScope()

    private val inputValid = MutableStateFlow(true)

    val isInputValid = inputValid.asStateFlow()

    fun processTurningOp(turningOp: TurningOperation) {
        val programName = "Test_od_turning.ngc"
        val convProgram =
            ConversationalProgram(
                programName = programName,
                // todo replace with kotlin-date
                creationDate = clock.now(),
                operations = listOf(turningOp)
            )

        val programLines = convProgram.generateGCode()
        programLines.forEach { println(it) }
        fileSystemRepository.writeProgramLines(programLines, programName)
    }
}
