package com.mindovercnc.data.linuxcnc.legacy

import com.mindovercnc.data.linuxcnc.CncCommandRepository
import com.mindovercnc.data.linuxcnc.CncStatusRepository
import com.mindovercnc.data.linuxcnc.HalRepository
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val LinuxcncLegacyDataModule = DI.Module("linuxcnc_legacy_data") {
    bindSingleton<CncCommandRepository> { CncCommandRepositoryLegacy() }
    bindSingleton<HalRepository> { HalRepositoryLegacy() }
    bindSingleton<CncStatusRepository> { CncStatusRepositoryLegacy(instance(), instance()) }
}
