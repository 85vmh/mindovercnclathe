package com.mindovercnc.dispatchers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import java.util.concurrent.Executors

actual
val DispatchersModule =
    DI.Module("dispatchers") {
        bindSingleton { MainDispatcher(Dispatchers.Main) }
        bindSingleton { IoDispatcher(Dispatchers.IO) }
        bindProvider {
            val dispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
            NewSingleThreadDispatcher(dispatcher)
        }
    }