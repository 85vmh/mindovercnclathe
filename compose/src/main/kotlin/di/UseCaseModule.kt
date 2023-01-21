package di

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import usecase.*
import usecase.FeedUseCase
import usecase.SpindleUseCase
import usecase.TaperUseCase

val UseCaseModule =
  DI.Module("UseCase") {
    bindSingleton { MessagesUseCase(messagesRepository = instance()) }

    bindSingleton {
      ManualTurningUseCase(
        ioDispatcher = instance(),
        cncStatusRepository = instance(),
        commandRepository = instance(),
        messagesRepository = instance(),
        halRepository = instance(),
        settingsRepository = instance(),
        iniFileRepository = instance()
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
        statusRepository = instance(),
        halRepository = instance(),
        settingsRepository = instance(),
        iniFileRepository = instance(),
        activeLimitsRepository = instance()
      )
    }

    bindSingleton {
      SimpleCyclesUseCase(
        ioDispatcher = instance(),
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
        ioDispatcher = instance(),
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

    bindSingleton { BreadCrumbDataUseCase() }

    bindSingleton { FileSystemDataUseCase(instance()) }
  }
