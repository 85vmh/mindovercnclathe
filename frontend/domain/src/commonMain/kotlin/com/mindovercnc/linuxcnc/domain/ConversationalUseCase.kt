package com.mindovercnc.linuxcnc.domain

import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.dispatchers.createScope
import com.mindovercnc.model.codegen.ConversationalProgram
import com.mindovercnc.model.codegen.operation.TurningOperation
import com.mindovercnc.repository.FileSystemRepository
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

@Deprecated("This is a deprecated class")
class ConversationalUseCase(
    ioDispatcher: IoDispatcher,
    private val clock: Clock,
    private val fileSystemRepository: FileSystemRepository
) {
    private val scope = ioDispatcher.createScope()

    fun processTurningOp(turningOp: TurningOperation) {
        val programName = "Test_od_turning.ngc"
        val convProgram =
            ConversationalProgram(
                programName = programName,
                creationDate = clock.now(),
                operations = listOf(turningOp)
            )

        val programLines = convProgram.generateGCode()
        programLines.forEach { println(it) }
        scope.launch {
            fileSystemRepository.writeProgramLines(programLines, programName)
        }
    }
}
