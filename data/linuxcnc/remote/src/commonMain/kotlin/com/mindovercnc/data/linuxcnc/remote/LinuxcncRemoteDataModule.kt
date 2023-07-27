package com.mindovercnc.data.linuxcnc.remote

import com.mindovercnc.data.linuxcnc.CncCommandRepository
import com.mindovercnc.data.linuxcnc.CncStatusRepository
import com.mindovercnc.data.linuxcnc.HalRepository
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val LinuxcncRemoteDataModule = DI.Module("linuxcnc_remote_data") {
    bindSingleton<CncCommandRepository> {
        CncCommandRepositoryImpl(instance())
    }
    bindSingleton<CncStatusRepository> {
        CncStatusRepositoryImpl(instance())
    }
    bindSingleton<HalRepository> {
        HalRepositoryImpl(instance())
    }
}