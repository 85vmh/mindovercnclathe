package di

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import usecase.*

val DomainModule = DI.Module("domain") {
    bindSingleton { MessagesUseCase(messagesRepository = instance()) }

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
            latheToolsRepository = instance(),
            cuttingInsertsRepository = instance(),
            varFileRepository = instance()
        )
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
            statusRepository = instance(),
            commandRepository = instance(),
            settingsRepository = instance(),
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
            gCodeRepository = instance(),
        )
    }

    bindSingleton { ActiveCodesUseCase(instance()) }

    bindSingleton { BreadCrumbDataUseCase() }

    bindSingleton { FileSystemDataUseCase(instance()) }

    bindSingleton { DtgPositionUseCase(instance()) }
    bindSingleton { G5xPositionUseCase(instance()) }
    bindSingleton { MachineUsableUseCase(instance()) }
}
