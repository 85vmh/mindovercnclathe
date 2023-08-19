package com.mindovercnc.linuxcnc.di

import com.mindovercnc.linuxcnc.*
import com.mindovercnc.repository.*
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val CommonDataModule =
    DI.Module("common_data") {
        bindSingleton<EmcMessagesRepository> { EmcMessagesRepositoryImpl(instance(), instance()) }
        bindSingleton<CncMessagesRepository> { CncMessagesRepositoryImpl(instance()) }

        bindSingleton<TaskStatusRepository> { TaskStatusRepositoryImpl(instance()) }
        bindSingleton<MotionStatusRepository> { MotionStatusRepositoryImpl(instance()) }
        bindSingleton<IoStatusRepository> { IoStatusRepositoryImpl(instance()) }

        bindSingleton<ActiveLimitsRepository> { ActiveLimitsRepositoryImpl() }
    }
