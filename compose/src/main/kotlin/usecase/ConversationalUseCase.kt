package usecase

import codegen.ConversationalProgram
import codegen.TurningOperation
import com.mindovercnc.repository.CncCommandRepository
import com.mindovercnc.repository.CncStatusRepository
import com.mindovercnc.repository.FileSystemRepository
import com.mindovercnc.repository.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

class ConversationalUseCase(
    private val scope: CoroutineScope,
    private val statusRepository: CncStatusRepository,
    private val commandRepository: CncCommandRepository,
    private val settingsRepository: SettingsRepository,
    private val fileSystemRepository: FileSystemRepository
) {

    private val inputValid = MutableStateFlow(true)

    val isInputValid = inputValid.asStateFlow()


    fun processTurningOp(turningOp: TurningOperation) {

        val programName = "Test_od_turning.ngc"
        val convProgram = ConversationalProgram(
            programName = programName,
            creationDate = Date(),
            operations = listOf(turningOp)
        )

        val programLines = convProgram.generateGCode()
        programLines.forEach {
            println(it)
        }
        fileSystemRepository.writeProgramLines(programLines, programName)
    }
}