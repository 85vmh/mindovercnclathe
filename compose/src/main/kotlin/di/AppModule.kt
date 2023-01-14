package di

import StatusWatcher
import TabViewModel
import okio.FileSystem
import org.kodein.di.*

val AppModule =
  DI.Module("AppModule") {
    bindSingleton { StatusWatcher(instance(), instance()) }

    bindProvider {
      TabViewModel(
        instance(),
      )
    }

    bindSingleton { FileSystem.SYSTEM }
  }
