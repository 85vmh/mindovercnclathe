package com.mindovercnc.linuxcnc.domain

import com.mindovercnc.data.linuxcnc.FileSystemRepository
import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.dispatchers.createScope
import com.mindovercnc.model.codegen.ConversationalProgram
import com.mindovercnc.model.codegen.operation.TurningOperation
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import mu.KotlinLogging

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
        programLines.forEach { LOG.debug { it } }
        scope.launch { fileSystemRepository.writeProgramLines(programLines, programName) }
    }

    companion object {
        private val LOG = KotlinLogging.logger("ConversationalUseCase")
    }
}
