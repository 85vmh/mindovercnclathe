package com.mindovercnc.data.lathehal.local.di

import com.mindovercnc.data.lathehal.HalRepository
import com.mindovercnc.data.lathehal.local.HalRepositoryLocal
import org.kodein.di.DI
import org.kodein.di.bindSingleton

val LatheHalLocalDataModule = DI.Module("lathehal_local_data") {
    bindSingleton<HalRepository> { HalRepositoryLocal() }
}
