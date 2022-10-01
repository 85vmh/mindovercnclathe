package di

import TabViewModel
import org.kodein.di.*
import StatusWatcher

val AppModule = DI.Module("AppModule") {
    bindSingleton {
        StatusWatcher(
            instance(),
            instance()
        )
    }

    bindProvider {
        TabViewModel(
            instance(),
        )
    }
}