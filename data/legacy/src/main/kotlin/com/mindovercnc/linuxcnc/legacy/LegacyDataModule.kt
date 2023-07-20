package com.mindovercnc.linuxcnc.legacy

import com.mindovercnc.repository.CncCommandRepository
import com.mindovercnc.repository.CncStatusRepository
import com.mindovercnc.repository.HalRepository
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val LegacyDataModule = DI.Module("legacy_data") {
    bindSingleton<CncCommandRepository> { CncCommandRepositoryLegacy() }
    bindSingleton<HalRepository> { HalRepositoryLegacy() }
    bindSingleton<CncStatusRepository> { CncStatusRepositoryLegacy(instance(), instance()) }
}