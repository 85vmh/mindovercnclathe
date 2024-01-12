package com.mindovercnc.linuxcnc.settings.remote.di

import com.mindovercnc.linuxcnc.settings.SettingsRepository
import com.mindovercnc.linuxcnc.settings.remote.SettingsRepositoryRemote
import org.kodein.di.DI
import org.kodein.di.bindProvider

val SettingsRemoteModule = DI.Module("settings_remote") {
    bindProvider<SettingsRepository> { SettingsRepositoryRemote() }
}