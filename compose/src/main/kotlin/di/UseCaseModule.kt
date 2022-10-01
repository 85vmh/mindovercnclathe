package di

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import usecase.FeedUseCase
import usecase.SpindleUseCase
import usecase.TaperUseCase
import usecase.*

val UseCaseModule = DI.Module("UseCase") {
    bindSingleton {
        MessagesUseCase(messagesRepository = instance())
    }

    bindSingleton {
        ManualTurningUseCase(
            scope = instance(tag = "app_scope"),
            statusRepository = instance(),
            commandRepository = instance(),
            messagesRepository = instance(),
            halRepository = instance(),
            settingsRepository = instance(),
            iniFileRepository = instance()
        )
    }

    bindSingleton {
        AngleFinderUseCase(
            scope = instance(tag = "app_scope"),
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
            scope = instance(tag = "app_scope"),
            statusRepository = instance(),
            halRepository = instance(),
            settingsRepository = instance(),
            iniFileRepository = instance(),
            activeLimitsRepository = instance()
        )
    }

    bindSingleton {
        SimpleCyclesUseCase(
            scope = instance(tag = "app_scope"),
            statusRepository = instance(),
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
            scope = instance(tag = "app_scope"),
            statusRepository = instance(),
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
            scope = instance(tag = "app_scope"),
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
            scope = instance(tag = "app_scope"),
            statusRepository = instance(),
            commandRepository = instance(),
            settingsRepository = instance(),
            fileSystemRepository = instance()
        )
    }

    bindSingleton {
        ProgramsUseCase(
            statusRepository = instance(),
            commandRepository = instance(),
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
            statusRepository = instance(),
            halRepository = instance(),
        )
    }

    bindSingleton {
        TaperUseCase(
            settingsRepository = instance(),
        )
    }

    bindSingleton {
        PositionUseCase(
            statusRepository = instance(),
        )
    }

    bindSingleton {
        GCodeUseCase(
            gCodeRepository = instance(),
        )
    }

    bindSingleton {
        ActiveCodesUseCase(
            instance(),
        )
    }
}