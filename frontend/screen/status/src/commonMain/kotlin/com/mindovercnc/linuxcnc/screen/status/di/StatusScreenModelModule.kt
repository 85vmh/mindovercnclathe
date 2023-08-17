package com.mindovercnc.linuxcnc.screen.status.di

import com.mindovercnc.linuxcnc.screen.status.StatusRootScreenModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

val StatusScreenModelModule =
    DI.Module("status_screen_model") { bindProvider { StatusRootScreenModel(instance()) } }
