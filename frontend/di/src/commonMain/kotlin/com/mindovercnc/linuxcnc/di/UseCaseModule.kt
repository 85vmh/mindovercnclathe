package com.mindovercnc.linuxcnc.di

import com.mindovercnc.linuxcnc.domain.*
import com.mindovercnc.linuxcnc.domain.gcode.impl.ArcFeedGcodeCommandParser
import com.mindovercnc.linuxcnc.domain.gcode.impl.CommentGcodeCommandParser
import com.mindovercnc.linuxcnc.domain.gcode.impl.StraightGcodeCommandParser
import com.mindovercnc.linuxcnc.domain.tools.CuttingInsertUseCase
import com.mindovercnc.linuxcnc.domain.tools.LatheToolUseCase
import com.mindovercnc.linuxcnc.domain.tools.ToolHolderUseCase
import com.mindovercnc.linuxcnc.domain.tools.ToolsUseCase
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

private val gcodeCommandParsers =
    mapOf(
        "STRAIGHT_TRAVERSE" to StraightGcodeCommandParser,
        "STRAIGHT_FEED" to StraightGcodeCommandParser,
        "ARC_FEED" to ArcFeedGcodeCommandParser,
        "COMMENT" to CommentGcodeCommandParser
    )

val DomainModule =
    DI.Module("domain") {
        bindSingleton { MessagesUseCase(instance(), instance()) }

        bindSingleton {
            ManualTurningUseCase(
                ioDispatcher = instance(),
                cncStatusRepository = instance(),
                motionStatusRepository = instance(),
                commandRepository = instance(),
                messagesRepository = instance(),
                halRepository = instance(),
                settingsRepository = instance(),
                iniFileRepository = instance(),
            )
        }

        bindSingleton {
            AngleFinderUseCase(
                ioDispatcher = instance(),
                statusRepository = instance(),
                commandRepository = instance(),
                messagesRepository = instance(),
                halRepository = instance(),
                settingsRepository = instance(),
                iniFileRepository = instance()
            )
        }

        bindSingleton {
            VirtualLimitsUseCase(
                ioDispatcher = instance(),
                taskStatusRepository = instance(),
                ioStatusRepository = instance(),
                halRepository = instance(),
                settingsRepository = instance(),
                iniFileRepository = instance(),
                activeLimitsRepository = instance()
            )
        }

        bindSingleton {
            SimpleCyclesUseCase(
                ioDispatcher = instance(),
                taskStatusRepository = instance(),
                commandRepository = instance(),
                halRepository = instance(),
                settingsRepository = instance(),
            )
        }

        bindSingleton {
            ManualPositionUseCase(
                cncStatusRepository = instance(),
            )
        }

        bindSingleton {
            ToolsUseCase(
                ioDispatcher = instance(),
                statusRepository = instance(),
                motionStatusRepository = instance(),
                ioStatusRepository = instance(),
                commandRepository = instance(),
                messagesRepository = instance(),
                halRepository = instance(),
                settingsRepository = instance(),
                toolHolderRepository = instance(),
                latheToolRepository = instance(),
                varFileRepository = instance()
            )
        }

        bindSingleton { CuttingInsertUseCase(cuttingInsertsRepository = instance()) }

        bindSingleton { LatheToolUseCase(latheToolRepository = instance()) }

        bindSingleton {
            ToolHolderUseCase(toolHolderRepository = instance(), latheToolRepository = instance())
        }

        bindSingleton {
            ManualToolChangeUseCase(
                ioDispatcher = instance(),
                halRepository = instance(),
            )
        }

        bindSingleton {
            OffsetsUseCase(
                statusRepository = instance(),
                commandRepository = instance(),
                varFileRepository = instance()
            )
        }

        bindSingleton {
            ConversationalUseCase(
                ioDispatcher = instance(),
                clock = instance(),
                fileSystemRepository = instance()
            )
        }

        bindSingleton {
            ProgramsUseCase(
                commandRepository = instance(),
                dtgPositionUseCase = instance(),
                g5xPositionUseCase = instance(),
            )
        }

        bindSingleton {
            SpindleUseCase(
                settingsRepository = instance(),
                statusRepository = instance(),
                halRepository = instance()
            )
        }

        bindSingleton {
            FeedUseCase(
                settingsRepository = instance(),
                statusRepository = instance(),
            )
        }

        bindSingleton {
            HandWheelsUseCase(
                taskStatusRepository = instance(),
                halRepository = instance(),
            )
        }

        bindSingleton {
            TaperUseCase(
                settingsRepository = instance(),
            )
        }

        bindSingleton {
            PositionUseCase(statusRepository = instance(), dtgPositionUseCase = instance())
        }

        bindSingleton {
            GCodeUseCase(
                gCodeInterpreterRepository = instance(),
                ioDispatcher = instance(),
                parsers = gcodeCommandParsers
            )
        }

        bindSingleton { ActiveCodesUseCase(instance()) }

        bindSingleton { BreadCrumbDataUseCase(instance()) }

        bindSingleton { FileSystemDataUseCase(instance()) }

        bindSingleton { DtgPositionUseCase(instance()) }
        bindSingleton { G5xPositionUseCase(instance()) }
        bindSingleton { MachineUsableUseCase(instance()) }
    }
