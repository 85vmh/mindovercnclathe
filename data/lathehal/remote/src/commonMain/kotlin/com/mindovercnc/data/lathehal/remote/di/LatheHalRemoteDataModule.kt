package com.mindovercnc.data.lathehal.remote.di

import com.mindovercnc.data.lathehal.HalRepository
import com.mindovercnc.data.lathehal.remote.HalRepositoryRemote
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val LatheHalRemoteDataModule = DI.Module("lathehal_remote_data") {
    bindSingleton<HalRepository> {
        HalRepositoryRemote(instance())
    }
}
