package com.mindovercnc.dispatchers

import kotlinx.coroutines.Dispatchers
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton

actual
val DispatchersModule =
    DI.Module("dispatchers") {
        bindSingleton { MainDispatcher(Dispatchers.Main) }
        bindSingleton { IoDispatcher(Dispatchers.Default) }
        bindProvider {
            // todo
            NewSingleThreadDispatcher(Dispatchers.Default)
        }
    }